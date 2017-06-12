package src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by douglas.leite on 23/05/2017.
 * <p>
 * Classe para pegar o xml da maquina de Miley e transformar em uma sequencia de comandos.
 */
class XMLParser {
    static MealyMachine createMachine(String fileName) throws IOException, SAXException, ParserConfigurationException {
        try {
            File file = new File("tests/" + fileName + ".jff");

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);

            MealyMachine machine = new MealyMachine();

            if (doc.hasChildNodes()) {
                printNote(machine, doc.getChildNodes());
            }
            return machine;
        } catch (Exception e) {
            throw e;
        }
    }

    private static void printNote(MealyMachine machine, NodeList nodeList) {
        Status status;

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node tempNode = nodeList.item(i);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (Objects.equals(tempNode.getNodeName(), "state")) {
                    if (tempNode.hasAttributes()) {
                        // get attributes names and values
                        NamedNodeMap nodeMap = tempNode.getAttributes();

                        String name = nodeMap.getNamedItem("name").getNodeValue();
                        int id = Integer.parseInt(nodeMap.getNamedItem("id").getNodeValue());

                        NodeList childNodes = tempNode.getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            if (childNodes.item(j).getNodeName() == "initial") {
                                machine.initialStatus = id;
                            }
                        }

                        status = new Status(name, id);
                        machine.statuses.put(id, status);
                    }
                } else if (Objects.equals(tempNode.getNodeName(), "transition")) {
                    NodeList transitionNodes = tempNode.getChildNodes();
                    Transition transition = new Transition();
                    Status fromStatus = new Status();
                    for (int j = 0; j < transitionNodes.getLength(); j++) {
                        Node transitionTemp = transitionNodes.item(j);
                        String s;
                        Node n;
                        switch (transitionTemp.getNodeName()) {
                            case "from":
                                n = transitionTemp.getFirstChild();
                                s = n.getNodeValue();
                                fromStatus = machine.statuses.get(Integer.parseInt(s));
                                transition.from = fromStatus;
                                break;
                            case "to":
                                n = transitionTemp.getFirstChild();
                                s = n.getNodeValue();
                                transition.to = machine.statuses.get(Integer.parseInt(s));
                                break;
                            case "read":
                                n = transitionTemp.getFirstChild();
                                if (n != null)
                                    transition.read = n.getNodeValue();
                                break;
                            case "transout":
                                n = transitionTemp.getFirstChild();
                                if (n != null)
                                    transition.transout = n.getNodeValue();
                                break;
                        }
                    }
                    HashMap<String, Transition> t = machine.transitions.getOrDefault(fromStatus.id, new HashMap<>());
                    t.put(transition.read, transition);
                    machine.transitions.put(fromStatus.id, t);
                } else if (tempNode.hasChildNodes()) {
                    // loop again if has child nodes
                    printNote(machine, tempNode.getChildNodes());
                }
            }
        }

    }

}
