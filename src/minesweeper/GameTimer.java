package minesweeper;

public class GameTimer implements Runnable {
  Thread thread;
  Segment disp;
  boolean play=true;
  boolean threadSuspended=false;

  public GameTimer(Segment count) {
    disp=count;
  }
  public void run() {
    while (play) {
      try {
        thread.sleep(1000);
        if (threadSuspended) {
          synchronized(this) {
            while (threadSuspended)
              wait();
          }
        }
      }
      catch(java.lang.InterruptedException e) {}
      int time = disp.getValue();
      if (play && time<999) {//faire plus de 999s, c'est quand même beaucoup...
        disp.setValue(time+1);
      }

    }
  }
  public void start() {
    if (thread==null) thread = new Thread(this);
    thread.setPriority(thread.MAX_PRIORITY);
    thread.start();
  }
  public void stop() {
    if (thread!=null) thread = null;
  }
  public void cancel() {
    play=false;
  }
  public void suspend() {
    threadSuspended=true;
  }
  public synchronized void resume() {
    threadSuspended=false;
    notify();
  }
}

