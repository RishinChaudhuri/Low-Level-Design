package entities;

public class DoublyLinkedList<K, V>
{
    private Node<K, V> head;
    private Node<K, V> tail;

    public DoublyLinkedList()
    {
        this.head = null;
        this.tail = null;
    }

    public void addFront(Node<K, V> node)
    {
        if(this.head == null)
        {
            this.head = node;
            this.tail = node;
            return;
        }
        node.next = this.head;
        this.head.prev = node;
        this.head = node;
    }

    private Node remove(Node<K, V> node)
    {
        if(this.head == this.tail && this.head == node)
        {
            this.head = null;
            this.tail = null;
        }
        else if(this.head == node)
        {
            Node<K, V> currHead = this.head;
            this.head = this.head.next;
            currHead.next = null;
            this.head.prev = null;
        }
        else if(this.tail == node)
        {
            Node<K, V> currTail = this.tail;
            this.tail = this.tail.prev;
            this.tail.next = null;
            currTail.prev = null;
        }
        else
        {
            Node<K, V> prev = node.prev;
            Node<K, V> next = node.next;
            prev.next = next;
            next.prev = prev;
        }
        return node;
    }

    public Node removeLast()
    {
        return this.remove(this.tail);
    }

    public void moveToFront(Node<K, V> node)
    {
        this.remove(node);
        this.addFront(node);
    }
}
