package com.christroup.gashboard.sample.client;

import com.christroup.gashboard.wrapper.client.BackButton;
import com.christroup.gashboard.wrapper.client.DashboardWidget;
import com.christroup.gashboard.wrapper.client.InfoButton;
import com.christroup.gashboard.wrapper.client.WidgetOptions;
import com.christroup.gashboard.wrapper.client.events.WidgetDragEvent;
import com.christroup.gashboard.wrapper.client.events.WidgetDragHandler;
import com.christroup.gashboard.wrapper.client.events.WidgetVisibilityEvent;
import com.christroup.gashboard.wrapper.client.events.WidgetVisibilityHandler;
import com.christroup.gashboard.wrapper.client.shared.RPCRequest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
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
			String text();
		}
	}
	
	HTML rpc = new HTML("Gashboard includes a wrapper for GWT RPC. Click the button to make an RPC call.");
	int shown = 0;
	int hidden = 0;

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
		
		rpc.setStylePrimaryName(SampleResources.INSTANCE.css().text());
		front.add(rpc);
		front.add(new Button("RPC Call", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				performRPCCall();
			}
		}));
		
		HTML moveHandlers = new HTML("<br />Try moving the widget. It supports drag handlers!");
		moveHandlers.setStyleName(SampleResources.INSTANCE.css().text());
		final HTML moveHandlersMessage = new HTML();
		moveHandlersMessage.setStyleName(SampleResources.INSTANCE.css().text());
		
		front.add(moveHandlers);
		front.add(moveHandlersMessage);
		
		addWidgetDragHandler(new WidgetDragHandler() {
			public void onDragStart(WidgetDragEvent event) {
				moveHandlersMessage.setHTML("<strong>Dragging...</strong>");
				
			}
			public void onDragEnd(WidgetDragEvent event) {
				moveHandlersMessage.setHTML("<strong>Drag ended</strong>");
			}
		});
		
		
		HTML visibilityHandlers = new HTML("<br />The counter below will tell you how many times the widget has been hidden/shown:");
		visibilityHandlers.setStyleName(SampleResources.INSTANCE.css().text());
		final HTML visibilityHandlersMessage = new HTML();
		visibilityHandlersMessage.setStyleName(SampleResources.INSTANCE.css().text());
		
		front.add(visibilityHandlers);
		front.add(visibilityHandlersMessage);
		
		addWidgetVisibilityHandler(new WidgetVisibilityHandler() {
			
			@Override
			public void onShow(WidgetVisibilityEvent event) {
				shown++;
				visibilityHandlersMessage.setHTML("<strong>Shown: " + shown + " times and Hidden: " + hidden + " times</strong>");
			}
			
			@Override
			public void onHide(WidgetVisibilityEvent event) {
				hidden++;
				visibilityHandlersMessage.setHTML("<strong>Shown: " + shown + " times and Hidden: " + hidden + " times</strong>");
			}
		});
		
	}
	
	private void performRPCCall() {
		/*
		 * If we are running in hosted mode this will query the local service. If this is a compiled
		 * widget it will make an RPC call to an appengine service.
		 */
		TestServiceAsync svc;
		if (GWT.isScript()) {
			svc = RPCRequest.crossdomain(GWT.create(TestService.class), "http://gashboard.appspot.com/sample/test");
		} else {
			svc = GWT.create(TestService.class);
		}
		
		svc.myTestMethod(Window.Navigator.getUserAgent(), new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				rpc.setHTML(rpc.getHTML() + " <strong>" + result + "</strong> ");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
			}
		});
	}
	

}
