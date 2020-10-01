package sc.fiji.bdvpg.scijava.command.bdv;

import bdv.util.*;
import bdv.viewer.render.AccumulateProjectorFactory;
import net.imglib2.type.numeric.ARGBType;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import sc.fiji.bdvpg.bdv.BdvCreator;
import sc.fiji.bdvpg.bdv.projector.AccumulateAverageProjectorARGB;
import sc.fiji.bdvpg.bdv.projector.AccumulateMixedProjectorARGBFactory;
import sc.fiji.bdvpg.bdv.projector.Projection;
import sc.fiji.bdvpg.scijava.ScijavaBdvDefaults;
import sc.fiji.bdvpg.scijava.services.SourceAndConverterBdvDisplayService;
import sc.fiji.bdvpg.services.SourceAndConverterServices;

@Plugin(type = Command.class, menuPath = ScijavaBdvDefaults.RootMenu+"BDV>Create Empty BDV Frame",
    label = "Creates an empty BDV window")
public class BdvWindowCreatorCommand implements Command {

    @Parameter(label = "Create a 2D BDV window")
    public boolean is2D = false;

    @Parameter(label = "Title of the new BDV window")
    public String windowTitle = "BDV";

    @Parameter(label = "Interpolate")
    public boolean interpolate = false;

    @Parameter(label = "Number of timepoints (1 for a single timepoint)")
    public int nTimepoints = 1;

    @Parameter(choices = { Projection.MIXED_PROJECTOR, Projection.SUM_PROJECTOR, Projection.AVERAGE_PROJECTOR})
    public String projector;

    /**
     * This triggers: {@link sc.fiji.bdvpg.scijava.processors.BdvHandlePostprocessor}
     */
    @Parameter(type = ItemIO.OUTPUT)
    public BdvHandle bdvh;

    @Override
    public void run() {
        //------------ BdvHandleFrame
        BdvOptions opts = BdvOptions.options().frameTitle(windowTitle);
        if (is2D) opts = opts.is2D();

        // Create accumulate projector factory
        AccumulateProjectorFactory< ARGBType > factory = null;
        switch (projector) {
            case Projection.MIXED_PROJECTOR:
                factory = new AccumulateMixedProjectorARGBFactory(  );
                opts = opts.accumulateProjectorFactory(factory);
            case Projection.SUM_PROJECTOR:
                // Default projector
                break;
            case Projection.AVERAGE_PROJECTOR:
                factory = AccumulateAverageProjectorARGB.factory;
                opts = opts.accumulateProjectorFactory(factory);
                break;
            default:
        }

        BdvCreator creator = new BdvCreator(opts, interpolate, nTimepoints);
        creator.run();
        bdvh = creator.get();

        final SourceAndConverterBdvDisplayService displayService = SourceAndConverterServices.getSourceAndConverterDisplayService();

        switch (projector) {
            case Projection.MIXED_PROJECTOR:
                displayService.setDisplayMetadata( bdvh, Projection.PROJECTOR, Projection.MIXED_PROJECTOR );
                break;
            case Projection.SUM_PROJECTOR:
                displayService.setDisplayMetadata( bdvh, Projection.PROJECTOR, Projection.SUM_PROJECTOR );
                break;
            case Projection.AVERAGE_PROJECTOR:
                displayService.setDisplayMetadata( bdvh, Projection.PROJECTOR, Projection.AVERAGE_PROJECTOR );
                break;
            default:
        }
    }

}
