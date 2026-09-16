package rate_limiter;

public class TokenBucketRateLimiter implements RateLimiter
{
    private final long BUCKET_SIZE;
    private final long REFILL_RATE;

    private long tokens;
    private long lastRefillTime;

    public TokenBucketRateLimiter(long bucketSize, long refillRate)
    {
        this.BUCKET_SIZE = bucketSize;
        this.REFILL_RATE = refillRate;
        this.tokens = bucketSize;
        this.lastRefillTime = System.nanoTime();
    }

    public synchronized void acquire() throws InterruptedException
    {
        while(true)
        {
            refill();
            if(this.tokens > 1)
            {
                this.tokens -= 1;
                return;
            }

            double secondsToWait = 1.0 / this.REFILL_RATE;

            long millis = (long)(secondsToWait * 1000);

            Thread.sleep(Math.max(1, millis));
        }
    }

    private void refill()
    {
        long currentTime = System.nanoTime();
        long timeElapsed = currentTime - this.lastRefillTime;

        double secondsElapsed = timeElapsed / 1_000_000_000.0;
        double newTokens = secondsElapsed * this.REFILL_RATE;

        this.tokens = Math.min(this.BUCKET_SIZE, this.tokens + (long)newTokens);
        this.lastRefillTime = currentTime;

    }
}
