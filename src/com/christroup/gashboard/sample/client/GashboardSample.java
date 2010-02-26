package com.christroup.gashboard.sample.client;

import com.christroup.gashboard.wrapper.client.BackButton;
import com.christroup.gashboard.wrapper.client.DashboardWidget;
import com.christroup.gashboard.wrapper.client.InfoButton;
import com.christroup.gashboard.wrapper.client.WidgetOptions;
import com.christroup.gashboard.wrapper.client.shared.RPCRequest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

@WidgetOptions(
		height=380, 
		width=280, 
		allowNetworkAccess=true,
		mainHTML="index.html",
		displayName="Gashboard Sample Widget",
		version="1.0"
)
public class GashboardSample extends DashboardWidget {
	
	interface SampleResources extends ClientBundle {
		public static SampleResources INSTANCE = GWT.create(SampleResources.class);
		
		ImageResource front();
		ImageResource back();
		ImageResource gwt();
		
		@Source("main.css")
		CSS css();
		
		interface CSS extends CssResource {
			String front();
			String back();
			String title();
		}
	}

	protected void widgetBack(AbsolutePanel back) {
		SampleResources.INSTANCE.css().ensureInjected();
		
		back.addStyleName(SampleResources.INSTANCE.css().back());
		
		Image gwt = new Image(SampleResources.INSTANCE.gwt());
		
		back.add(gwt);
		
		BackButton b = new BackButton("Back to Front");
		back.add(b);
		
	}

	protected void widgetFront(final AbsolutePanel front) {
		SampleResources.INSTANCE.css().ensureInjected();
		
		front.addStyleName(SampleResources.INSTANCE.css().front());
		
		InfoButton info = new InfoButton();
		front.add(info);
		
		Label title = new Label("Gashboard Sample Widget");
		title.setStylePrimaryName(SampleResources.INSTANCE.css().title());
		
		front.add(title);
		
		TestServiceAsync svc = RPCRequest.crossdomain(GWT.create(TestService.class), "http://gashboard.appspot.com/sample/test");
		svc.myTestMethod("test", new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				front.add(new Label(result));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
			}
		});
		
	}
	

}
