class Node{
	public int id;
	public int order;
	Node prev;
	Node next;
	
	public Node(int i,int orde){
		id=i;
		order=orde;
	}
}

public class doublelinkedlist{
	public Node head=null;
	public Node tail=null;

	public void add(int id,int order){
		Node newnode = new Node(id,order);

		if(head==null){
			head=tail=newnode;
			head.prev=null;
			tail.next=null;
		}
		else{
			tail.next=newnode;
			newnode.prev=tail;
			tail=newnode;
			tail.next=null;
		}

	}


	public void delete(){
		if(head==tail){
			head=null;
			tail=null;
		}
		else{
			Node newnode;
			newnode=head.next;
			head=newnode;
		}
	}

}