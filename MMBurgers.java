class Costumerinfo{
    public int id;
    public int order;
    public int arrivaltime;
    public int departaltime;
    public int quenefreetime;
    public int state;
    public boolean end=false;
}

class Stoves{
    public boolean status=false;
    public int time=0;
}

public class MMBurgers implements MMBurgersInterface {
    public int time=0;
    public Stoves[] stoves;
    public MyQuene[] counter;
    public Costumerinfo[] info = new Costumerinfo[1];
    public int totalnumberofcostumer=0;
    public MyHeap orderheap=new MyHeap();
    public int totalnumberofcounters;
    public int totalnumberofstoves;
    public int previousid=0;

    public boolean isEmpty(){
        for(int i=0;i<totalnumberofcostumer;i++)
            if(info[i].state!=totalnumberofcounters+2)
                return false;
            //if(i.state!=counter.length+2)
              //  return false;
            return true;
    } 
    
    public void setK(int k) throws IllegalNumberException{
        if(k<=0)
            throw new IllegalNumberException("The number of counters has to be possitive");
        counter = new MyQuene[k];
        for(int i=0;i<k;i++)
        counter[i] = new MyQuene();
        totalnumberofcounters=k;	
    }   
    
    public void setM(int m) throws IllegalNumberException{
        if(m<=0)
            throw new IllegalNumberException("Number of stoves has to be possitive");
        stoves = new Stoves[m];
        for(int i=0;i<m;i++){
        stoves[i] = new Stoves();
        } 
        totalnumberofstoves=m;
    } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        if(id!=previousid+1)
            throw new IllegalNumberException("The id is not continous");
        if(totalnumberofcostumer==info.length){
            Costumerinfo[] temp = new Costumerinfo[totalnumberofcostumer];
            temp = info;
            info = new Costumerinfo[totalnumberofcostumer*2];
            for(int i=0;i<totalnumberofcostumer;i++)
                info[i]=temp[i];
        }
        info[id-1]=new Costumerinfo();
        info[id-1].id=id;
        info[id-1].order=numb;
        info[id-1].arrivaltime=t;
        info[id-1].state=0;
        totalnumberofcostumer++;
        previousid=id;

        
    } 


    public int minqueneindex(){
        int temp=counter[0].size();
        int temp2=0;
        for(int i=0;i<totalnumberofcounters;i++){
            if(temp>counter[i].size())
                temp=counter[i].size();
        }
        for(int i=0;i<totalnumberofcounters;i++){
            if(counter[i].size()==temp){
                temp2=i;
                break;
            }
        }
        return temp2;
    }


    public void advanceTime(int t) throws IllegalNumberException{
        
        
        if(time==0){ 
        for(int i=0;i<totalnumberofcostumer;i++){
            if(info[i].arrivaltime==0){
                int temp= minqueneindex();
               
               if(counter[temp].empty())
                info[i].quenefreetime=time+temp+1;
               else
                info[i].quenefreetime=info[counter[temp].lastid()-1].quenefreetime+temp+1;

               counter[temp].MyEnquene(info[i].id,info[i].order);
               info[i].state=temp+1;
            }
        }
        }

        if(time>t)
            throw new IllegalNumberException("The time is already passed");
        if(time==t)
            return;
        time=time+1;

       
        for(int i=0;i<totalnumberofcounters;i++){

            
            if(counter[i].empty()==false){
               
                if(info[counter[i].frontid()-1].quenefreetime==time){
                    orderheap.addorder(info[counter[i].frontid()-1].id,info[counter[i].frontid()-1].order,time-i);
                    info[counter[i].frontid()-1].state=totalnumberofcounters+1;

                    counter[i].MyDequene();
                }
            }
        }
        //this will take care of the stoves
        
        Stovesregulator();
        

        //now the only thing left that if any costumer in come in then we have to 
        for(int i=0;i<totalnumberofcostumer;i++){
            if(info[i].departaltime==time){
                info[i].state++;
                info[i].end=true;
            }
            if(info[i].arrivaltime==time){
                //System.out.println("desirable quende index"+minqueneindex());
               int temp= minqueneindex();
               if(counter[temp].empty()){
                info[i].quenefreetime=time+temp+1;
                //System.out.println("the id "+i+"time"+time);

            }
            else{
                info[i].quenefreetime=info[counter[temp].lastid()-1].quenefreetime+temp+1;
            }

               counter[temp].MyEnquene(info[i].id,info[i].order);
               info[i].state=temp+1;
            }
        }
        advanceTime(t);






    } 
    
    public int Freestoves(){
        int temp=0;
        for(Stoves i:stoves)
            if(i.status==false)
                temp++;
        return temp;
    }

    public void Stovesregulator(){
        for(Stoves i:stoves){
            if(i.time==10){
                i.status=false;
                i.time=0;
            }
            else if(i.time!=0){
                i.time++;
            }
        }
        Stovesregulator2();
    }

    public void Stovesregulator2(){
        if(Freestoves()!=0&&orderheap.empty()==false){

            

            if(Freestoves()<orderheap.top().order){
                for(Stoves i:stoves){
                    if(i.status==false){
                        i.status=true;
                        i.time=1;
                        orderheap.top().order=orderheap.top().order-1;
                    }
                }
            }
            else if(Freestoves()==orderheap.top().order){
                

                for(Stoves i:stoves)
                    if(i.status==false){
                        i.status=true;
                        i.time=1;
                    }
                   // System.out.println(orderheap.top().costumerid+"the id of which the sstate is changeing"+time);
                info[orderheap.top().costumerid-1].departaltime=time+11;
                orderheap.deleteMin();
            }
            else{

                for(Stoves i:stoves){
                    
                    if(0==orderheap.top().order){
                        break;
                    }
                    if(i.status==false){
                        i.status=true;
                        i.time=1;
                        
                        orderheap.top().order=orderheap.top().order-1;
                    }
                }
                
                //info[orderheap.top().costumerid-1].state=totalnumberofcounters+1;
                //System.out.println(orderheap.top().costumerid+"the id of which the sstate is changeing"+time);
                info[orderheap.top().costumerid-1].departaltime=time+11;
                orderheap.deleteMin();

                Stovesregulator2();
            }
        }

    }

    

    public int customerState(int id, int t) throws IllegalNumberException{
        advanceTime(t);
        if(id>totalnumberofcostumer)
            throw new IllegalNumberException("There is no costurmer with this id");
        return info[id-1].state;
    } 

    public int griddleState(int t) throws IllegalNumberException{
        advanceTime(t);
        
            return totalnumberofstoves-Freestoves();
    } 

    public int griddleWait(int t) throws IllegalNumberException{
        advanceTime(t);
        return orderheap.totalburgerwaiting();
    } 

    public int customerWaitTime(int id) throws IllegalNumberException{
        if(id>totalnumberofcostumer||id<=0)
            throw new IllegalNumberException("There is no one with this id");
                return info[id-1].departaltime-info[id-1].arrivaltime;
    } 

	public float avgWaitTime(){
        float temp=0;
        for(int i=0;i<totalnumberofcostumer;i++){
            temp = temp+info[i].departaltime-info[i].arrivaltime;
        }
        return temp/totalnumberofcostumer;
        
    } 

    
}





