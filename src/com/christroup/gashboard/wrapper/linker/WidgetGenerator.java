package com.christroup.gashboard.wrapper.linker;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import com.christroup.gashboard.wrapper.client.WidgetOptions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class WidgetGenerator extends Generator {

	  @Override
	  public String generate(TreeLogger logger, GeneratorContext context,
	      String typeName) throws UnableToCompleteException {
		  
	    // The TypeOracle knows about all types in the type system
	    TypeOracle typeOracle = context.getTypeOracle();

	    // Get a reference to the type that the generator should implement
	    JClassType sourceType = typeOracle.findType(typeName);

	    // Ensure that the requested type exists
	    if (sourceType == null) {
	      logger.log(TreeLogger.ERROR, "Could not find requested typeName", null);
	      throw new UnableToCompleteException();
	    }

	    // Make sure the Gadget type is correctly defined
	    validateType(logger, sourceType);

	    // Pick a name for the generated class to not conflict.
	    String generatedSimpleSourceName = sourceType.getSimpleSourceName()
	        + "WidgetImpl";

	    // Begin writing the generated source.
	    ClassSourceFileComposerFactory f = new ClassSourceFileComposerFactory(
	        sourceType.getPackage().getName(), generatedSimpleSourceName);
	    f.addImport(GWT.class.getName());
	    f.setSuperclass(typeName);

	    // All source gets written through this Writer
	    PrintWriter out = context.tryCreate(logger,
	        sourceType.getPackage().getName(), generatedSimpleSourceName);

	    // If an implementation already exists, we don't need to do any work
	    if (out != null) {

	      // We really use a SourceWriter since it's convenient
	      SourceWriter sw = f.createSourceWriter(context, out);
	      sw.println("public " + generatedSimpleSourceName + "() {");
	      sw.indent();
	      sw.println("nativeInit();");

	      sw.outdent();
	      sw.println("}");

	      sw.println("private native void nativeInit() /*-{");
	      sw.indent();

	      sw.outdent();
	      sw.println("}-*/;");

	      // Write out the manifest
	      OutputStream manifestOut = context.tryCreateResource(logger, "Info.plist");
	      if (manifestOut == null) {
	        logger.log(TreeLogger.ERROR, "Widget manifest was already created",
	            null);
	        throw new UnableToCompleteException();
	      }

	      generateWidgetManifest(logger, typeOracle, sourceType, new PrintWriter(
	          new OutputStreamWriter(manifestOut)));
	      context.commitResource(logger, manifestOut);

	      sw.commit(logger);
	    }

	    return f.getCreatedClassName();
	  }

	protected void generateWidgetManifest(TreeLogger logger,
			TypeOracle typeOracle, JClassType type, Writer out)
			throws UnableToCompleteException {

		logger = logger.branch(TreeLogger.INFO, "Building widget manifest",
				null);

		Document d;
		LSSerializer serializer;
		LSOutput output;

		// Initialize the XML document
		try {
			DOMImplementationRegistry registry = DOMImplementationRegistry
					.newInstance();
			DOMImplementation impl = registry.getDOMImplementation("Core 3.0");
			d = impl.createDocument(null, "plist", null);
			DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature(
					"LS", "3.0");
			output = implLS.createLSOutput();
			output.setCharacterStream(out);
			serializer = implLS.createLSSerializer();
		} catch (ClassNotFoundException e) {
			logger.log(TreeLogger.ERROR, "Could not create document", e);
			throw new UnableToCompleteException();
		} catch (IllegalAccessException e) {
			logger.log(TreeLogger.ERROR, "Could not create document", e);
			throw new UnableToCompleteException();
		} catch (InstantiationException e) {
			logger.log(TreeLogger.ERROR, "Could not create document", e);
			throw new UnableToCompleteException();
		}

		d.getDocumentElement().setAttribute("version", "1.0");
		Element dict = (Element) d.getDocumentElement().appendChild(d.createElement("dict"));
		
		WidgetOptions options = type.getAnnotation(WidgetOptions.class);
		
		addDictItem(d, dict, "AllowNetworkAccess", options.allowNetworkAccess());
		addDictItem(d, dict, "CFBundleDevelopmentRegion", options.language());
		addDictItem(d, dict, "CFBundleDisplayName", options.displayName());
		addDictItem(d, dict, "CFBundleIdentifier", type.getPackage().getName());
		addDictItem(d, dict, "CFBundleName", type.getName());
		addDictItem(d, dict, "CFBundleShortVersionString", options.version());
		addDictItem(d, dict, "CFBundleVersion", options.version());
		addDictItem(d, dict, "CloseBoxInsetX", 15);
		addDictItem(d, dict, "CloseBoxInsetY", 15);
		addDictItem(d, dict, "MainHTML", options.mainHTML());
		addDictItem(d, dict, "Width", options.width());
		addDictItem(d, dict, "Height", options.height());
		
		serializer.write(d, output);
	}
	
	private void addDictItem(Document d, Element dict, String key, String value) {
		Element keyEl = d.createElement("key");
		keyEl.appendChild(d.createTextNode(key));
		dict.appendChild(keyEl); 
		
		Element valueEl = d.createElement("string");
		valueEl.appendChild(d.createTextNode(value));
		dict.appendChild(valueEl);
	}
	
	private void addDictItem(Document d, Element dict, String key, int value) {
		Element keyEl = d.createElement("key");
		keyEl.appendChild(d.createTextNode(key));
		dict.appendChild(keyEl); 
		
		Element valueEl = d.createElement("integer");
		valueEl.appendChild(d.createTextNode(Integer.toString(value)));
		dict.appendChild(valueEl);
	}
	
	private void addDictItem(Document d, Element dict, String key, boolean value) {
		Element keyEl = d.createElement("key");
		keyEl.appendChild(d.createTextNode(key));
		dict.appendChild(keyEl); 
		
		if (value) {
			dict.appendChild(d.createElement("true"));
		} else {
			dict.appendChild(d.createElement("false"));
		}
	}

	protected void validateType(TreeLogger logger, JClassType type)
			throws UnableToCompleteException {
		if (!type.isDefaultInstantiable()) {
			logger.log(TreeLogger.ERROR,
					"Widget types must be default instantiable", null);
			throw new UnableToCompleteException();
		}
	}
}
