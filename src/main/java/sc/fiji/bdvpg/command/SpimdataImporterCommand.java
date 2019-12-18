package sc.fiji.bdvpg.command;

import mpicbg.spim.data.SpimData;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import sc.fiji.bdvpg.scijava.ScijavaBdvDefaults;
import sc.fiji.bdvpg.spimdata.importer.SpimDataImporterXML;

import java.io.File;

@Plugin( type = Command.class, menuPath = ScijavaBdvDefaults.RootMenu+"Tools>SpimData>Spimdata from XML File" )
public class SpimdataImporterCommand implements Command {

    @Parameter
    File f;

    @Parameter(type = ItemIO.OUTPUT)
    SpimData spimData;

    public void run() {
        spimData = new SpimDataImporterXML(f).get();
    }

}
