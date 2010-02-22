package com.christroup.gashboard.wrapper.linker;

import java.io.File;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.linker.AbstractLinker;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.EmittedArtifact;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.impl.StandardLinkerContext;

@LinkerOrder(LinkerOrder.Order.POST)
public class WidgetLinker extends AbstractLinker {

	@Override
	public String getDescription() {
		return "OSX Dashboard Widget";
	}

	@Override
	public ArtifactSet link(TreeLogger logger, LinkerContext context, ArtifactSet artifacts) throws UnableToCompleteException {
		
		ArtifactSet toReturn = new ArtifactSet(artifacts);

		for (EmittedArtifact res : toReturn.find(EmittedArtifact.class)) {
			if (res.getPartialPath().endsWith("hosted.html")) {
				toReturn.remove(res);
				break;
			}
		}
		
		if (context instanceof StandardLinkerContext) {
			File output = new File("war/" + context.getModuleName() + ".wdgt");
			logger.log(Type.INFO, "Creating Widget Directory: war/" + context.getModuleName() + ".wdgt");
			((StandardLinkerContext) context).produceOutputDirectory(logger, toReturn, output);
		}
		
		return new ArtifactSet(artifacts);
	}

}
