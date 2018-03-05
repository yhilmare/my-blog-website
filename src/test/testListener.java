package test;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class testListener implements HttpSessionListener, ServletContextListener {

	private List<HttpSession> list = Collections.synchronizedList(new LinkedList<HttpSession>());
	private Object lock = new Object();
	
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Timer timer = new Timer();
		timer.schedule(new myTask(list, lock), 0, 1000*10);
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		System.out.println("Session created");
		synchronized (lock) {
			this.list.add(session);
		}
	}
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Session destoried");
	}
}


class myTask extends TimerTask{

	private Object lock;
	private List<HttpSession> list;
	
	public myTask(List list, Object lock){
		super();
		this.lock = lock;
		this.list = list;
	}
	
	public void run() {
		synchronized (this.lock) {
			System.out.println(new Date().toLocaleString());
			ListIterator iterator = this.list.listIterator();
			while(iterator.hasNext()){
				HttpSession session = (HttpSession) iterator.next();
				if((System.currentTimeMillis() - session.getLastAccessedTime()) > 1000*20){
					session.invalidate();
					iterator.remove();
				}
			}
		}	
	}
}