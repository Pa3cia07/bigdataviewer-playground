package sc.fiji.bdvpg.scijava.command.bdv;

import bdv.util.BdvHandle;
import bdv.viewer.SourceAndConverter;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import sc.fiji.bdvpg.bdv.navigate.ViewerTransformAdjuster;
import sc.fiji.bdvpg.scijava.ScijavaBdvDefaults;
import sc.fiji.bdvpg.scijava.services.BdvSourceAndConverterDisplayService;
import sc.fiji.bdvpg.sourceandconverter.display.BrightnessAutoAdjuster;

@Plugin(type = Command.class, menuPath = ScijavaBdvDefaults.RootMenu+"Bdv>Show Sources")
public class BdvSourcesAdderCommand implements Command {

    @Parameter
    BdvHandle bdvh;

    @Parameter
    SourceAndConverter[] sacs;

    @Parameter
    BdvSourceAndConverterDisplayService bsds;

    @Parameter
    boolean autoContrast;

    @Parameter
    boolean adjustViewOnSource;

    @Override
    public void run() {
        for (SourceAndConverter sac : sacs ) {
            bsds.show(bdvh, sac);
            int timepoint = bdvh.getViewerPanel().getState().getCurrentTimepoint();
            if (autoContrast) {
                new BrightnessAutoAdjuster(sac, timepoint).run();
            }
        }
        if ((adjustViewOnSource) && ( sacs.length>0)) {
            new ViewerTransformAdjuster(bdvh, sacs[0]).run();
        }
    }
}