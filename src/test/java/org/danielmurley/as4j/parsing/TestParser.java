package org.danielmurley.as4j.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxSvgCanvas;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxDomUtils;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.mxCellRenderer.CanvasFactory;
import com.mxgraph.view.mxGraph;
import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.io.json.JsonWriter;
import com.structurizr.model.Element;
import com.structurizr.model.Model;
import com.structurizr.model.Relationship;

public class TestParser {

	@Test
	public void test_parse_basic_script()
	{
		Yaml yaml = new Yaml();
		try {
			InputStream is = Files.newInputStream(Paths.get(TestParser.class.getResource("/architecture-script3.arch").toURI()));
			Object o = yaml.load(is);
			Model m = new Model();

			List<HashMap> lo = (List) o;
			for (HashMap<String, String> hm : lo)
			{
				EntityMappings.buildNewMappingForType(hm.get("type")).mapFieldsToModel(m, hm);
			}
			System.err.println("Applying later relationships");
			
			NonThreadSafeParsingContext.applyAndFlushLaterRelationshipsToModel(m);
			mxGraph graph = new mxGraph();
			graph.setHtmlLabels(true);
			Object parent = graph.getDefaultParent();

			
			graph.setHtmlLabels(true);
			
			graph.getModel().beginUpdate();
			
			mxCell vx = (mxCell) graph.insertVertex(parent, "999","daniel is great", 100, 100, 100, 100, "verticalLabelPosition=top;verticalAlign=bottom;opacity=50");
			
			
			
			parent = vx;
			
			try {
			
				Set<Element> elements = m.getElements();
				
				HashMap links = new HashMap();
				
				for (Element e : elements)
				{
					String style = StyleAdapter.getStyle(e);
					
					vx = (mxCell) graph.insertVertex(parent, e.getId(),e.getName(), 100, 100, StyleAdapter.getWidth(e), StyleAdapter.getHeight(e), style);
					if (e.getName().equals("<<ft system>>"))
					{
						vx.setParent((mxICell) graph.getDefaultParent()); 
					}
					
					System.err.println("inserted " + e + " at " + e.getId());
					
					links.put(e.getId(), vx);
				}
				
				
				Set<Relationship> relationships = m.getRelationships();
				
				for (Relationship r : relationships)
				{
					
					Object src = links.get(r.getSourceId());
					Object dest = links.get(r.getDestinationId());
			
					Object rx = graph.insertEdge(parent, r.getId(), Optional.of(r.getDescription()).orElse("hello"), src, dest, "dashed=true");
				}
				
//				Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
//				Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
//				
//				Object v3 = graph.insertVertex(parent, null, "this", 240, 150, 80, 30);
//				Object v4 = graph.insertVertex(parent, null, "is", 240, 150, 80, 30);
//				Object v5 = graph.insertVertex(parent, null, "awesome", 240, 150, 80, 30);
//				
//				Object v6 = graph.insertVertex(parent, null, "less", 240, 150, 80, 30);
//				Object v7 = graph.insertVertex(parent, null, "than", 240, 150, 80, 30);
//				Object v8 = graph.insertVertex(parent, null, "great", 240, 150, 80, 30);
//				
//				
//				
//				graph.insertEdge(parent, null, "Edge", v1, v2);
//				graph.insertEdge(parent, null, "Edge", v1, v3);
//				graph.insertEdge(parent, null, "Edge", v1, v4);
//				graph.insertEdge(parent, null, "Edge", v1, v5);
//				
//				graph.insertEdge(parent, null, "Edge", v6, v2);
//				graph.insertEdge(parent, null, "Edge", v7, v2);
//				graph.insertEdge(parent, null, "Edge", v8, v2);
//				
				
				//mxOrganicLayout layout = new mxOrganicLayout(graph);
				//mxCircleLayout layout = new mxCircleLayout(graph);
				mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
				layout.setInterRankCellSpacing(100);
				layout.setIntraCellSpacing(200);
				layout.setParentBorder(20);
				
				//mxStackLayout layout = new mxStackLayout(graph);
				
				
				layout.execute(parent);
				
			} finally {
				graph.getModel().endUpdate();
			}

			mxGraphComponent graphComponent = new mxGraphComponent(graph);
		
			
			mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer
					.drawCells(graph, null, 1, null,
							new CanvasFactory()
							{
								public mxICanvas createCanvas(
										int width, int height)
								{
									mxSvgCanvas canvas = new mxSvgCanvas(
											mxDomUtils.createSvgDocument(
													width, height));
									canvas.setEmbedded(true);

									return canvas;
								}

							});

			try {
				mxUtils.writeFile(mxXmlUtils.getXml(canvas.getDocument()),
						"/tmp/my.svg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
//			Workspace ws = new Workspace("My model", "I like models");
//			StructurizrClient structurizrClient = new StructurizrClient("https://api.structurizr.com", "1bb59656-c53d-4a0f-beff-758fe8d3b51f", "b73f214e-0e98-42fa-9fa4-147d4b9d57cd");
//			structurizrClient.getWorkspace(3331);
//			ws.setModel(m);
//			
//			 JsonWriter jsonWriter = new JsonWriter(true);
//		     StringWriter stringWriter = new StringWriter();
//		     jsonWriter.write(ws, stringWriter);
//			 System.err.println(stringWriter.toString());
//			
//			structurizrClient.putWorkspace(3331, ws);
//			
			
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
