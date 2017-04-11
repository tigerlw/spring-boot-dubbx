package com.ucloudlink.endport;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ucloudlink.dubbox.service.CallbackListener;
import com.ucloudlink.dubbox.service.CallbackService;
import com.ucloudlink.dubbox.service.MockService;
import com.ucloudlink.dubbox.service.UserService;

import rx.Observer;
import rx.subjects.PublishSubject;

@RestController
public class ConsumerController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MockService mockService;
	
	@Autowired
	private CallbackService callbackService;
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String queryUser(@RequestParam String userName)
	{
		String result = userService.queryUser(userName);
		
		return result;
	}
	
	@RequestMapping(value="/hello2", method=RequestMethod.GET)
	public String startMock(@RequestParam String userName)
	{
		String result = mockService.startMock(userName);
		
		return result; 
	}
	
	@RequestMapping(value="/callback", method=RequestMethod.GET)
	public String callBackService(@RequestParam String msg)
	{

		final PublishSubject<String> stringPublishSubject = PublishSubject.create();

		stringPublishSubject.subscribe(new Observer<String>() {

			private AtomicLong count = new AtomicLong(0);

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNext(String t) {
				// TODO Auto-generated method stub
				System.out.println(t + "========" + count.incrementAndGet());
				
				if("liuwei".equals(t))
				{
					
					callbackService.addListener(t+"2", new CallbackListener() {

						@Override
						public void changed(String msg) {
							// TODO Auto-generated method stub
							System.out.println(msg);
							
							stringPublishSubject.onNext(msg);
						}

					});
					
				}
			}

		});

		callbackService.addListener(msg, new CallbackListener() {

			@Override
			public void changed(String msg) {
				// TODO Auto-generated method stub
				System.out.println(msg);
				
				stringPublishSubject.onNext(msg);
			}

		});
		
		
		return msg;
	}

}
