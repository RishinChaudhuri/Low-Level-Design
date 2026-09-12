import entities.DoublyLinkedList;
import entities.Node;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LRUCache<K, V>
{
    private final int capacity;
    private final AtomicInteger size = new AtomicInteger(0);
    private final Map<K, Node> map = new ConcurrentHashMap<K, Node>();
    private final DoublyLinkedList<K, V> list = new DoublyLinkedList<K,V>();

    public LRUCache(int capacity)
    {
        this.capacity = capacity;
    }

    public synchronized V get(K key)
    {
        if(!map.containsKey(key))
        {
            return null;
        }
        Node node = this.map.get(key);
        V value = (V) node.getValue();
        this.list.moveToFront(node);
        return value;

    }

    public synchronized void put(K key, V value)
    {
        if(this.size.get() == this.capacity)
        {
            Node node = this.list.removeLast();
            this.map.remove(node.getKey());
            this.size.decrementAndGet();
        }
        this.size.incrementAndGet();
        Node newEntry = new Node(key, value);
        this.list.addFront(newEntry);
        map.put(key, newEntry);
    }

}