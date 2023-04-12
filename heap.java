class Heapnode{
    public int order;
    public int costumerid;
    public int priority;
}

class MyHeap{
    public Heapnode[] heaparray;
    public int pendingorder=0;
    
    public MyHeap(){
        heaparray = new Heapnode[2];
    }

    public Heapnode top(){
            return heaparray[1];
    }

    public boolean empty(){
        if(pendingorder==0)
            return true;
        return false;
    }

    public int totalburgerwaiting(){
        int temp=0;
        for(int i=1;i<pendingorder+1;i++)
            temp+=heaparray[i].order;
        return temp;

    }

    public void addorder(int id, int noofburgers,int prior){
            if(pendingorder+1==heaparray.length){
                Heapnode[] temp = new Heapnode[pendingorder+1];
                temp = heaparray;
                heaparray = new Heapnode[(pendingorder+1)*2];
                for(int i=1; i<pendingorder+1;i++){
                    heaparray[i]=new Heapnode();
                    heaparray[i]=temp[i];
                }

            }
                heaparray[pendingorder+1]=new Heapnode();
                heaparray[pendingorder+1].order=noofburgers;
                heaparray[pendingorder+1].costumerid=id;
                heaparray[pendingorder+1].priority=prior;
                
                heapproperty(0);
                pendingorder++;
    }

   



    public void deleteMin(){
        heaparray[1]=heaparray[pendingorder];
        heaparray[pendingorder]=null;
        heapproperty(1);
        pendingorder--;


    }

    public void heapproperty(int i){
        Heapnode temp2 = new Heapnode();

        if(i==0){
            int temp = pendingorder+1;
            if(temp==1)
                return;
            while(heaparray[temp/2].priority>heaparray[temp].priority){
                temp2=heaparray[temp/2];
                heaparray[temp/2]=heaparray[temp];
                heaparray[temp]=temp2;
                temp=temp/2;
                if(temp==1)
                    return;
            }

        }
        else{
            int j =1;
           
            while(2*j<=pendingorder-1){
                if(pendingorder-1>2*j){

                if(heaparray[j].priority>heaparray[(2*j)].priority||heaparray[j].priority>heaparray[(2*j)+1].priority){
                    if(heaparray[(2*j)].priority>heaparray[(2*j)+1].priority){
                        temp2 = heaparray[j];
                        heaparray[j]=heaparray[(2*j)+1];
                        heaparray[(2*j)+1]=temp2;
                        j=(2*j)+1;
                    }
                    else{
                        temp2 = heaparray[j];
                        heaparray[j]=heaparray[(2*j)];
                        heaparray[(2*j)]=temp2;
                        j=(2*j);
                    }
                }
            }
            else if(pendingorder-1==2*j){
                if(heaparray[j].priority>heaparray[(2*j)].priority){
                    
                        temp2 = heaparray[j];
                        heaparray[j]=heaparray[(2*j)];
                        heaparray[(2*j)]=temp2;
                        j=(2*j);
                    
                }

            }
        }

        }
    }



}
