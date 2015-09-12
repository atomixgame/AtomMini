/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.nifty.controls;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author cuongnguyen
 */
public class HintLayer {

    public static void createHintLayer(Nifty nifty, Screen screen) {
        new LayerBuilder("hintLayer") {
            {
                childLayoutAbsoluteInside();
                panel(new PanelBuilder("hintPanel") {
                    {
                        width("200px");
                        height("200px");
                        backgroundColor("#0009");
                        childLayoutVertical();
                        panel(new PanelBuilder("infoPanel") {
                            {
                                height("40px");
                                childLayoutHorizontal();

                                text(new TextBuilder("hintTitle") {
                                    {
                                        width("50%");
                                        text("hintTitle");
                                        style("defaultFont");
                                        color("#ffff");
                                        alignCenter();
                                        valignCenter();
                                    }
                                });

                            }
                        });

                        panel(new PanelBuilder("hintLongInfoPanel") {
                            {
                                childLayoutVertical();
                                text(new TextBuilder("hintLongInfo") {
                                    {
                                        style("defaultFont");
                                        color("#ffff");
                                        wrap(true);
                                        text("Long info");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty, screen, screen.getRootElement());
    }
}
