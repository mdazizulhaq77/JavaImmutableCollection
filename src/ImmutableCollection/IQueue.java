/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImmutableCollection;

import javafx.util.Pair;

/**
 *
 * @author test
 */
interface List<T>
{
    public List<T> push(T t);
    public List<T> pop();
}


class IList<T>
{
    private T head;
    private IList<T> tail;
    private int size;
    
    public IList()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    private IList(T t, IList<T> tail)
    {
      this.head = t;
      this.tail = tail; 
      this.size = tail != null ? (tail.size + 1): 0;

    }        
    IList<T> push(T t)
    {
       
       return new IList<T>(t, this);
    }
    
    Pair<T, IList<T>> pop() throws Exception
    {
       if (this.isEmpty())
           throw new Exception();
       return new Pair(head, new IList<T>(this.tail.head, this.tail.tail));   
    }        

    IList<T> reverse(IList<T> l, IList<T> t) throws Exception{
       
        if (t.tail == null)
            return l;
        else 
        {  
            return reverse(l.push(t.getHead()), t.pop().getValue());
        }
    }
    
    public int size()
    {
        return this.size;
    }    
    boolean isEmpty()
    {
        return size == 0;
    }
    public T getHead(){
      return this.head;  
    }

}


interface Queue<T> {
    public Queue<T> enQueue(T t);
    //Removes the element at the beginning of the immutable queue, and returns the new queue.
    public Queue<T> deQueue();
    public T head();
    public boolean isEmpty();
}


public class IQueue<T> implements Queue<T>{

    private IList<T> front;
    private IList<T> rear;
    private int size;
    
   
    public IQueue()
    {
       this.front = new IList<T>();
       this.rear = new IList<T>();
       this.size = 0;
    }
   
    
    private IQueue(IList<T> front, IList<T> rear)
    {
       this.front = front;
       this.rear = rear;
       this.size = front.size() + rear.size();
    }
    
    public Queue<T> enQueue(T t){
        return new IQueue<T>(this.front, this.rear.push(t));
    }
   
    public Queue<T> deQueue(){ 
        Queue<T> q = null;
        try{
          if ( front.isEmpty())
             q = new IQueue<T>(this.rear.reverse(new IList<T>(), this.rear).pop().getValue(), new IList<T>());
          else 
             q = new IQueue<T>(this.front.pop().getValue(), this.rear); 
        }
        catch(Exception e){}
        return q;

    }
    public T head(){
        
        try{
          if (this.front.isEmpty())
            return this.rear.reverse(new IList<T>(), this.rear).getHead();
        }
        catch(Exception e){}
        return front.getHead();
    }
    public boolean isEmpty(){return size == 0;}
    
    
    
    public static void main(String[] args) throws Exception
    {
       
       // List 
       IList<String> list;
       list= new IList<>(); 
       IList<String> t = list.push("1").push("2").push("3").push("4");
       assert t.size() == 4;
       
       assert t.pop().getKey() == "4";
       assert t.pop().getValue().pop().getKey() == "3";
       //System.out.println(t.pop().getValue().pop().getValue().pop().getKey());
       assert t.pop().getValue().pop().getValue().pop().getKey() == "2";
       assert t.pop().getValue().pop().getValue().pop().getValue().pop().getKey() == "1";
       
       assert t.push("5").pop().getKey() == "5" ;
       //t.pop().getValue().size();
       assert t.pop().getValue().pop().getValue().pop().getValue().pop().getValue().size() == 0;
       
       //Queue
       Queue<String> q = new IQueue<String>().enQueue("1").enQueue("2").enQueue("3").enQueue("4");
       
       assert q.head() == "1";
       assert q.deQueue().head() == "2";
       assert q.deQueue().deQueue().head() == "3";
       assert q.deQueue().deQueue().deQueue().deQueue().isEmpty();
       assert q.enQueue("1").enQueue("2").deQueue().head() == "2";
       
       
    }        


}



