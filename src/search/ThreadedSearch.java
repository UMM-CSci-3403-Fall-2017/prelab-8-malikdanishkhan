package search;

import java.util.ArrayList;

public class ThreadedSearch<T> implements Runnable {

  private T target;
  private ArrayList<T> list;
  private int begin;
  private int end;
  private Answer answer;

  public ThreadedSearch() {
  }

  private ThreadedSearch(T target, ArrayList<T> list, int begin, int end, Answer answer) {
    this.target=target;
    this.list=list;
    this.begin=begin;
    this.end=end;
    this.answer=answer;
  }

  public boolean parSearch(int numThreads, T target, ArrayList<T> list) throws{
    int section = list.size()/numThreads;
    Thread[] thread = new Thread[numThreads];
    ArrayList<Answer> result = new ArrayList<Answer>();

    begin = 0;
    end = section;

    for(int i = 0; i < numThreads; ++i){
       results.add(new Answer());
       thread[i] = new Thread(new ThreadedSearch<T>(target,list,begin,end,results.get(i)));

       thread[i].start();
       begin = begin + section;
       end = end + section;       
    }

     for(int j = 0; i < numThreads; ++j){
        try {
            thread[i].join();
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
       }

     for(int k = 0; k < result.size(); k++ ){
      if(result.get(k).answer){
         return true;
      }
     }
    return false;
  }

  @SuppressWarnings("unused")
  public void run() {
  search(this.target,this.list,this.start,this.end);
  }
  
  private void search(T target, ArrayList<T> list, int start, int end){
   for(int j = start; j < end; j++ ){
      if(list.get(j).equals(target)){
         answer = true;
       }
   }
  }

  private class Answer {
    public boolean answer = false;
  }

}
