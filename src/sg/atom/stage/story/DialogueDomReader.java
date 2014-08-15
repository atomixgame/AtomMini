/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.story;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sg.atom.core.io.xml.XMLDomUtil;

/**
 *
 * @author CuongNguyen
 */
public class DialogueDomReader {

    public Dialogue loadXML(String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(ClassLoader.getSystemResourceAsStream("sg/game/cuteheroes/gameplay/story/dialogue1.xml"));

//            List<DialogueNode> textNodeList = new ArrayList<DialogueNode>();

            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    if (node.getNodeName().equals("infos")) {
                        readInfosNodes(node);
                    } else if (node.getNodeName().equals("actions")) {
                        readActionsNodes(node);
                    } else if (node.getNodeName().equals("characters")) {
                        readCharactersNodes(node);
                    } else if (node.getNodeName().equals("conversations")) {
                        readConversationsNodes(node);
                    } else if (node.getNodeName().equals("texts")) {
                        readTextsNodes(node);
                    } else if (node.getNodeName().equals("links")) {
                        readLinksNodes(node);
                    }


                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Dialogue.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Dialogue.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dialogue.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void readInfosNodes(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void readActionsNodes(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void readCharactersNodes(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void readLinksNodes(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void readTextsNodes(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void readConversationsNodes(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void readDialogueNode(Node node, ArrayList<DialogueNode> results) {
        DialogueNode resultNode = new DialogueNode();
        resultNode.id = XMLDomUtil.readInt(node, "id", -1);

        NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);

            if (cNode instanceof Element) {
//                String content = cNode.getLastChild().getTextContent().trim();
//                switch (cNode.getNodeName()) {
//                    case "firstName":
//                        resultNode.firstName = content;
//                        break;
//                    case "lastName":
//                        resultNode.lastName = content;
//                        break;
//                    case "location":
//                        resultNode.location = content;
//                        break;
//                }
            }
        }
        results.add(resultNode);
    }
}
