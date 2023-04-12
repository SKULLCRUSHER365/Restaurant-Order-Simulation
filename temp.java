class MyQuene{
	public doublelinkedlist quene;
	public int elements=0;

	public MyQuene(){
		quene = new doublelinkedlist();
	}

	public int lastid(){
		return quene.tail.id;
	}

	public int frontid(){
		return quene.head.id;
	}

	public void MyEnquene(int id,int order){
		quene.add(id,order);
		elements++;

	}

	public void MyDequene(){
		quene.delete();
		elements--;
	}

	public int size(){
		return elements;
	}

	public boolean empty(){
		if(elements==0)
			return true;
		return false;
	}


}