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

  public boolean parSearch(int numThreads, T target, ArrayList<T> list){
    int section = list.size()/numThreads;
    Thread[] thread = new Thread[numThreads];
   

    begin = 0;
    end = section;

    for(int i = 0; i < numThreads; ++i){
       thread[i] = new Thread(new ThreadedSearch<T>(target,list,begin,end,answer));
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
    return answer.getAnswer();
  }

  public void run() {
   for (int a = 0; a < end; ++a) {
          if (list.get(a).equals(target)) {
              answer.setAnswer(true);
              break;
          }
      }
  }
 
  private class Answer {
    public boolean answer = false;
  
    public boolean getAnswer() {
      return answer;
    }
    // This has to be synchronized to ensure that no two threads modify
    // this at the same time, possibly causing race conditions.
    public synchronized void setAnswer(boolean newAnswer) {
      answer = newAnswer;
    }
  }
}
