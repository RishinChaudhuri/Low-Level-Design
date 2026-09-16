package rate_limiter;

public interface RateLimiter
{
    public abstract void acquire() throws InterruptedException;
}
