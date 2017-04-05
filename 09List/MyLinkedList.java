import java.util.*;

public class MyLinkedList implements Iterable<Integer> {

    public LNode start, end, current;
    public int size;

    public MyLinkedList(){
	start = null;
	end = null;
	current = start;
	size = 0;
    }

    public boolean add(int value){
	add(size, value);
	/*
	if(size == 0){
	    start = new LNode(value);
	    end = start;
	    size++;
	    return true;
	}
	end.next = new LNode(value);
	end = end.next;
	size++;*/
	return true;
    }

    public boolean add(int index, int value){
	if(size == 0){
	    start = new LNode(value);
	    end = start;
	    size++;
	    return true;
	}
	current = getNthNode(index-1);
	LNode newNode = new LNode(value);
	newNode.next = current.next;
	newNode.prev = current;
	current.next.prev = newNode;
	current.next = newNode;
	size++;
	return true;
    }
    
    private LNode getNthNode(int n){
	try{
	    current = start;
	    while(n != 0){
		current = current.next;
		n--;
	    }
	    return current;
	}catch(IndexOutOfBoundsException e){
	    System.out.println("Index out of bound");
	}
    }

    public int get(int index){
	return getNthNode(index).value;
    }

    public int set(int index, int newValue){
	int oldValue = get(index);
	current.value = newValue;
	return oldValue;
    }

    public int size(){
	return size;
    }
    
    public Iterator<Integer> iterator(){
	return new LinkedListIterator(this);
    }

    public String toString(){
	String temp = "[";
	current = start;
	while(current.next != null){
	    temp += current.toString();
	    //temp += "(" + current.prev.value + ")" + current.toString() + "(" + current.next.value + ")";
	    current = current.next;
	}
	temp += current.value + "]";
	return temp;
    }

    private class LNode{
	
	int value;
	LNode next, prev;

	public LNode(int value){
	    this.value = value;
	    next = null;
	}

	public String toString(){
	    return value + ",";
	}
    }



    public class LinkedListIterator implements Iterator<Integer> {

	public MyLinkedList list;
	public LNode location;
	
	public LinkedListIterator(MyLinkedList list){
	    this.list = list;
	    location = list.start;
	}

	public boolean hasNext(){
	    return location.next != null;
	}
	
	public Integer next(){
	    if (hasNext()){
		int value = location.value;
		location = location.next;
		return value;
	    }
	    else {
		throw new NoSuchElementException();
	    }
	}
        
	public void remove(){
	    throw new UnsupportedOperationException();
	}
    }


    public static void main(String[] args){
	MyLinkedList hello = new MyLinkedList();
	hello.add(7);
	hello.add(3);
	hello.add(5);
	hello.add(6);
	hello.add(13);
	hello.add(-4);
	System.out.println(hello.get(3));
	System.out.println(hello);
    }
}
