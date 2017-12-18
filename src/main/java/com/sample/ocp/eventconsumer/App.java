package com.sample.ocp.eventconsumer;

import java.util.List;

import com.openshift.internal.restclient.model.KubernetesEvent;
import com.openshift.restclient.ClientBuilder;
import com.openshift.restclient.IClient;
import com.openshift.restclient.IOpenShiftWatchListener;
import com.openshift.restclient.ResourceKind;
import com.openshift.restclient.model.IResource;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		IClient client = new ClientBuilder("https://master.ocp.com").withUserName("admin").withPassword("admin")
				.build();
		System.out.println("got client");
		
//		IResource request = client.getResourceFactory().stub(ResourceKind.PROJECT_REQUEST, "myfirstproject");
//		IProject project = (IProject) client.create(request);
		
		client.watch(new IOpenShiftWatchListener() {

			public void connected(List<IResource> resources) {
				System.out.println("connected");
			}

			public void disconnected() {
				System.out.println("disconnected");
			}

			public void received(IResource resource, ChangeType change) {
				System.out.println("received");
				KubernetesEvent event = (KubernetesEvent) resource;
				System.out.println("KubernetesEvent: " + event.toPrettyString());
			}

			public void error(Throwable err) {
				System.out.println("error");
			}

		}, ResourceKind.EVENT);
	}

}
