/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TextLoader implements AssetLoader {

    ArrayList<String> loadedLines = null;

    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        InputStream in = assetInfo.openStream();
        if (in != null) {

            loadedLines = new ArrayList<String>();
            BufferedReader bufRead = new BufferedReader(new InputStreamReader(in));
            String line = bufRead.readLine();
            while (line != null) {
                loadedLines.add(line);
                line = bufRead.readLine();

            }
            return loadedLines;
        }
        return null;
    }
}
