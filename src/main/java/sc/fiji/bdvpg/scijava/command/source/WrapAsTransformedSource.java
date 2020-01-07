package sc.fiji.bdvpg.scijava.command.source;

import bdv.viewer.Source;
import bdv.viewer.SourceAndConverter;
import net.imglib2.realtransform.AffineTransform3D;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import sc.fiji.bdvpg.scijava.ScijavaBdvDefaults;
import sc.fiji.bdvpg.sourceandconverter.transform.SourceAffineTransform;

import java.util.Arrays;
import java.util.stream.Collectors;

@Plugin(type = Command.class, menuPath = ScijavaBdvDefaults.RootMenu+"Tools>Wrap as Transformed Source")
public class WrapAsTransformedSource implements Command {
    @Parameter
    SourceAndConverter[] sources_in;

    @Parameter(type = ItemIO.OUTPUT)
    SourceAndConverter[] sources_out;

    @Override
    public void run() {
        SourceAffineTransform sat = new SourceAffineTransform(null, new AffineTransform3D());
        sources_out = Arrays.asList(sources_in).stream().map(sat::apply).collect(Collectors.toList()).toArray(new SourceAndConverter[sources_in.length]);
    }
}
