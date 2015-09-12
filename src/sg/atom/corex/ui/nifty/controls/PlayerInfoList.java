/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.nifty.controls;

import de.lessvoid.nifty.controls.ListBox.ListBoxViewConverter;
import de.lessvoid.nifty.elements.Element;
import sg.atom.gameplay.info.PlayerInfo;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.ui.NiftyGUIManager;

/**
 *
 * @author CuongNguyen
 */
public class PlayerInfoList implements ListBoxViewConverter<PlayerInfo> {

    private static final String INFO_LINE_ICON = "#player-info-line-icon";
    private static final String INFO_LINE_NAME = "#player-info-line-name";
    private static final String INFO_LINE_SCORE = "#player-info-line-score";
    private static final String INFO_LINE_NUM = "#player-info-line-num";

    /**
     * Default constructor.
     */
    public PlayerInfoList() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void display(final Element listBoxItem, final PlayerInfo item) {
        final Element name = listBoxItem.findElementById(INFO_LINE_NAME);
        final Element score = listBoxItem.findElementById(INFO_LINE_SCORE);
        final Element num = listBoxItem.findElementById(INFO_LINE_NUM);
        final Element icon = listBoxItem.findElementById(INFO_LINE_ICON);

        if (item != null) {
            NiftyGUIManager.setText(name, item.getName());
            NiftyGUIManager.setText(score, item.getScore());
            NiftyGUIManager.setText(num, item.getNum());
            NiftyGUIManager.setImage(icon, item.getIconImage());
        } else {
            NiftyGUIManager.setText(name, "No name");
            NiftyGUIManager.setText(score, "1");
            NiftyGUIManager.setText(num, "1");
            NiftyGUIManager.setImage(icon, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getWidth(final Element listBoxItem, final PlayerInfo item) {
//        final Element text = listBoxItem.findElementById(CHAT_LINE_TEXT);
//        final TextRenderer textRenderer = text.getRenderer(TextRenderer.class);
//        final Element icon = listBoxItem.findElementById(CHAT_LINE_ICON);
//        final ImageRenderer iconRenderer = icon.getRenderer(ImageRenderer.class);
//        return ((textRenderer.getFont() == null) ? 0 : textRenderer.getFont().getWidth(item.getLabel()))
//                + ((item.getIcon() == null) ? 0 : item.getIcon().getWidth());

        return 800;
    }
}
