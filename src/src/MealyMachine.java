package src;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Criado por Douglas Lima
 * Parte do projeto T2VeriVal
 * <p>
 * 25/05/2017.
 */

public class MealyMachine {
    int initialStatus;
    String fileName;
    HashMap<Integer, State> statuses;
    HashMap<Integer, HashMap<String, Transition>> transitions;

    public MealyMachine(String fileName) {
        statuses = new HashMap<>();
        transitions = new HashMap<>();
        this.fileName = fileName;
    }

    public void populateMachine() throws Exception {
        try {
            if(fileName.isEmpty())
                throw new InvalidParameterException("Nome do arquivo não informado");

            File f = new File(Paths.get("").toAbsolutePath().toString() + "/");
            File[] matchingFiles = f.listFiles((dir, name) -> name.contains(fileName) && name.endsWith("jff"));

            if (matchingFiles.length > 0) {
                File file = matchingFiles[0];

                DocumentBuilder dBuilder = null;
                try {
                    dBuilder = DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }

                Document doc = null;
                try {
                    if (dBuilder != null) {
                        doc = dBuilder.parse(file);
                    }
                } catch (SAXException | IOException e) {
                    e.printStackTrace();
                }

                if (doc != null && doc.hasChildNodes()) {
                    populateMachine(doc.getChildNodes());
                }
            } else
                throw new FileNotFoundException("Arquivo .jff não localizado");
        } catch (Exception e) {
            throw e;
        }
    }

    private void populateMachine(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node tempNode = nodeList.item(i);

            // Verifica se o nodo atual � um elemento
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                // Verifica se o nodo atual � um estado
                if (Objects.equals(tempNode.getNodeName(), "state")) {
                    //Trata quando o nodo for um Estado
                    handleState(tempNode);
                }
                // Verifica se o nodo atual � uma Transa��o
                if (Objects.equals(tempNode.getNodeName(), "transition")) {
                    //Trata quando o nodo for uma Transi��o
                    handleTransition(tempNode);
                }
                // Verifica se o nodo atual tem filhos para seguir na recurs�o
                if (tempNode.hasChildNodes()) {
                    populateMachine(tempNode.getChildNodes());
                }
            }
        }
    }

    private void handleState(Node node) {
        //Verifica se o nodo tem atributos (s� o estados tem nesse modelo)
        if (node.hasAttributes()) {
            NamedNodeMap nodeMap = node.getAttributes();

            String name = nodeMap.getNamedItem("name").getNodeValue();
            int id = Integer.parseInt(nodeMap.getNamedItem("id").getNodeValue());

            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (Objects.equals(childNodes.item(j).getNodeName(), "initial")) {
                    this.initialStatus = id;
                }
            }

            State state = new State(name, id);
            this.statuses.put(id, state);
        }
    }

    private void handleTransition(Node node) {
        NodeList transitionNodes = node.getChildNodes();
        Transition transition = new Transition();
        State fromState = new State();

        // Anda por todos nodos internos da transa��o
        for (int j = 0; j < transitionNodes.getLength(); j++) {
            Node transitionTemp = transitionNodes.item(j);
            String s;
            Node n;

            // Verifica o tipo do nodo interno da transa��o
            switch (transitionTemp.getNodeName()) {

                // Estado antes da transa��o
                case "from":
                    n = transitionTemp.getFirstChild();
                    s = n.getNodeValue();
                    fromState = this.statuses.get(Integer.parseInt(s));
                    transition.from = fromState;
                    break;

                // Estado depois
                case "to":
                    n = transitionTemp.getFirstChild();
                    s = n.getNodeValue();
                    transition.to = this.statuses.get(Integer.parseInt(s));
                    break;

                // Entrada
                case "read":
                    n = transitionTemp.getFirstChild();
                    if (n != null)
                        transition.read = n.getNodeValue();
                    break;

                // Sa�da
                case "transout":
                    n = transitionTemp.getFirstChild();
                    if (n != null)
                        transition.transout = n.getNodeValue();
                    break;
            }
        }
        HashMap<String, Transition> t = this.transitions.getOrDefault(fromState.id, new HashMap<>());
        t.put(transition.read, transition);
        this.transitions.put(fromState.id, t);
    }

    class State {
        String name;
        int id;

        State(String name, int id) {
            this.name = name;
            this.id = id;
        }

        State() {
        }
    }

    class Transition {
        State from;
        State to;

        String read;
        String transout;

        Transition() {
        }
    }
}