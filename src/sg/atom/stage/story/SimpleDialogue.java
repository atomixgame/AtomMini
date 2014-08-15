/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.story;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.TreeTraverser;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.stringtemplate.v4.ST;
import sg.atom.core.io.TextLoader;
import sg.atom.stage.GameAction;
import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.stage.StageManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SimpleDialogue {

    String title;
    int status;
    int lineNum;
    private int currentLine;
    private DialogueNode currentNode;
    private DialogueChoice parentNode = null;
//    List<String> alias;
//    List<String> actionAlias;
//    List<String> varAlias;    
    List<String> characterAlias;
    HashMap<String, GameAction> actionMap = new HashMap<String, GameAction>();
    HashMap<String, Object> vars = new HashMap<String, Object>();
    HashMap<String, GameCharacter> characterMap = new HashMap<String, GameCharacter>();
    List<GameCharacter> characters;
    String place;
    TreeTraverser<DialogueNode> dialogueTraverser;
    List<DialogueNode> sequence;
    GamePlayManager gamePlayManager;
    private StageManager stageManager;
    private AssetManager assetManager;
    String choicePrefix = "Choice";
    int NO_ANSWER = -1;

    public SimpleDialogue(String title, StageManager stageManager) {
        this.title = title;
        this.stageManager = stageManager;
        //this.gamePlayManager = gamePlayManager;
        this.assetManager = stageManager.getApp().getAssetManager();
    }

    public SimpleDialogue(String title, List<GameCharacter> characters, StageManager stageManager) {
        this.title = title;
        this.characters = characters;
        this.stageManager = stageManager;
        //this.gamePlayManager = gamePlayManager;
        this.assetManager = stageManager.getApp().getAssetManager();

        dialogueTraverser = new TreeTraverser<DialogueNode>() {
            @Override
            public Iterable<DialogueNode> children(DialogueNode root) {
                if (root instanceof DialogueChoice) {
                    return ((DialogueChoice) root).getOptions();
                } else {
                    return ImmutableList.of();
                }
            }
        };

    }

    public void initVars() {
        characterMap.put("B", new GameCharacter("Kiaira"));
        this.place = "Village";
    }

    public void load(String path) {
        assetManager.registerLoader(TextLoader.class, "txt");
        ArrayList<String> rawTexts = (ArrayList<String>) assetManager.loadAsset(path);

        lineNum = 0;
        sequence = new LinkedList<DialogueNode>();

        for (String line : rawTexts) {
//            System.out.println("RAW " + line);
            lineNum++;

            if (!line.startsWith("-")) {
                //It's a character line
                String characterAlias = line.substring(0, line.indexOf(":"));
                line = line.substring(characterAlias.length() + 1);
                if (line.isEmpty()) {
                    readLineContent(line, true, false, characterAlias);
                } else {
                    readLineContent(line, false, false, characterAlias);
                }
            } else {
                String characterAlias = line.substring(0, line.indexOf("-"));
                line = line.substring(characterAlias.length() + 1);
                readLineContent(line, false, true, parentNode.characterName);
            }
        }

        currentLine = 0;
        initVars();
    }

    private DialogueNode readLineContent(String line, boolean isChoice, boolean isInner, String characterAlias) {
        if (isChoice) {
            DialogueChoice choice = new DialogueChoice();
            choice.id = lineNum;
            choice.characterName = characterAlias;
            choice.text = null;
            parentNode = choice;
            sequence.add(choice);
            return choice;
        } else {
            DialogueNode node = new DialogueNode();

            String endAction = null;
            String contentText = null;
            if (line.endsWith("]")) {
                endAction = line.substring(line.lastIndexOf("[") + 1, line.lastIndexOf("]"));
//                System.out.println(" " + endAction);
                contentText = line.substring(0, line.lastIndexOf("["));
            } else {
                contentText = line.substring(0);
            }
            node.id = lineNum;
            node.characterName = characterAlias;
            node.text = contentText;
            node.actionName = endAction;
            if (!isInner) {
                sequence.add(node);
            } else {
                parentNode.addOption(node);
            }
            return node;
        }
    }

    public void start() {
        while (currentLine < sequence.size()) {

            DialogueNode node = sequence.get(currentLine);
            displayCharacter(node);
            if (node.text != null) {
                displayText(node);
            }
            if (node.characterName.equals(getMainCharacterName())) {
                waitForAnswer(node);
                break;
            }
            nextLine();
        }

    }

    public void end() {
        System.out.println("End of Dialogue!");
    }

    public void displayCharacter(DialogueNode node) {
        System.out.println("" + node.characterName + ":");
    }

    public void displayText(DialogueNode node) {
        ST st = new ST(node.text, '{', '}');

        st.add("place", place);
        st.add("B", characterMap.get("B"));
//        System.out.println(" " + node.text);
        System.out.println("" + st.render());
    }

    public void nextLine() {
        if (currentLine >= sequence.size()) {
            end();
        } else {
            currentLine++;
        }
    }

    public void waitForAnswer(DialogueNode node) {
        currentNode = node;
        if (node instanceof DialogueChoice) {
            List<DialogueNode> options = ((DialogueChoice) node).getOptions();
            for (int i = 0; i < options.size(); i++) {
                DialogueNode subNode = options.get(i);
                System.out.println("(" + (i + 1) + ")" + subNode.getText());
            }
            addMappings(options.size());
        } else {
            addMappings(NO_ANSWER);
        }
    }

    public void update(float tpf) {
    }

    public void onWaitForAnswer(float time) {
    }

    public void answered(int index) {

        if (index == NO_ANSWER) {
            nextLine();
        } else if (currentNode instanceof DialogueChoice) {

            List<DialogueNode> options = ((DialogueChoice) currentNode).getOptions();
            doAction(options.get(index).actionName);
            nextLine();
        }

        start();

    }

    public void doAction(String actionName) {
        System.out.println(" Do action " + actionName);
    }

    private void addMappings(int num) {

        InputManager inputManager = stageManager.getApp().getInputManager();
//        for (int i = 0; i < 9; i++) {
//            inputManager.deleteMapping(choicePrefix + i);
//        }
//        inputManager.deleteMapping("Next");

        // Add mappings again
        if (num == NO_ANSWER) {
            inputManager.addMapping("Next", new KeyTrigger(KeyInput.KEY_SPACE));
            inputManager.addListener(actionListener, "Next");
        } else {
            for (int i = 0; i < num; i++) {
                inputManager.addMapping(choicePrefix + i, new KeyTrigger(KeyInput.KEY_1 + i));
                inputManager.addListener(actionListener, choicePrefix + i);
            }
        }
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
            if (pressed) {
                if (name.contains(choicePrefix)) {
                    int num = Integer.parseInt(name.substring(name.lastIndexOf(choicePrefix) + choicePrefix.length()));
                    answered(num);
                } else if (name.equals("Next")) {
                    answered(NO_ANSWER);
                }
            }

        }
    };
// private AnalogListener analogListener = new AnalogListener() {
//        public void onAnalog(String name, float value, float tpf) {
//            System.out.println(name + " = " + value);
//        }
//};

    public String getMainCharacterName() {
        return "B";
    }
}
