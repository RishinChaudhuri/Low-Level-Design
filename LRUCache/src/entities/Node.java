package entities;

public class Node<K, V>
{
    private final K key;
    private final V value;
    protected Node<K,V> next;
    protected Node<K,V> prev;

    public Node(K key, V value)
    {
        this.key = key;
        this.value = value;
        this.next = null;
        this.prev = null;
    }

    public K getKey()
    {
        return this.key;
    }

    public V getValue()
    {
        return this.value;
    }
}
