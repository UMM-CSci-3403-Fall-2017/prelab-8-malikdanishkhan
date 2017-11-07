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
    target=target;
    list=list;
    begin=begin;
    end=end;
    answer=answer;
  }

  public boolean parSearch(int numThreads, T target, ArrayList<T> list){
    int section = list.size()/numThreads;
    Thread[] thread = new Thread[numThreads];
    ArrayList<Answer> result = new ArrayList<Answer>();

    begin = 0;
    end = section;

    for(int i = 0; i < numThreads; ++i){
       result.add(new Answer());
       thread[i] = new Thread(new ThreadedSearch<T>(target,list,begin,end,result.get(i)));

       thread[i].start();
       begin = begin + section;
       end = end + section;       
    }

     for(int j = 0; j < numThreads; ++j){
        try {
            thread[j].join();
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
  search(target,list,begin,end);
  }
  
  private void search(T target, ArrayList<T> list, int start, int end){
   for(int j = start; j < end; j++ ){
      if(list.get(j).equals(target)){
         answer.answer = true;
       }
   }
  }

  private class Answer {
    public boolean answer = false;
  }

}
