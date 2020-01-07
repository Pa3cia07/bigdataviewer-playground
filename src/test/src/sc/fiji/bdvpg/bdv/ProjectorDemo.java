package sc.fiji.bdvpg.bdv;

import bdv.util.BdvHandle;
import bdv.util.BdvOptions;
import mpicbg.spim.data.generic.AbstractSpimData;
import sc.fiji.bdvpg.bdv.BdvCreator;
import sc.fiji.bdvpg.bdv.navigate.ViewerTransformAdjuster;
import sc.fiji.bdvpg.bdv.source.display.BrightnessAutoAdjuster;
import sc.fiji.bdvpg.projector.AccumulateAverageProjectorARGB;
import sc.fiji.bdvpg.services.BdvService;
import sc.fiji.bdvpg.spimdata.importer.SpimDataImporterXML;

/**
 * Demonstrates average projection of two sources.
 *
 * TODO: make projection mode optional or dependent on some source metadata.
 */
public class ProjectorDemo
{
	public static void main( String[] args )
	{
		//final BdvHandle bdvHandle = createBdv();

		// add 1st source
		//final SourceAndConverter< ? > sourceAndConverter = new SourceAndConverterLoader( "src/test/resources/mri-stack.xml" ).getSourceAndConverter( 0 );
		//new SourceAndConverterBdvAdder( bdvHandle, sourceAndConverter ).run();
		//new ViewerTransformAdjuster( bdvHandle, sourceAndConverter.getSpimSource() ).run();
		//new BrightnessAdjuster( bdvHandle, sourceAndConverter, 10, 255.0 ).run();

		// add 2nd source
		//final SourceAndConverter< ? > sourceAndConverter2 = new SourceAndConverterLoader( "src/test/resources/mri-stack-shiftedX.xml" ).getSourceAndConverter( 0 );
		//new SourceAndConverterBdvAdder( bdvHandle, sourceAndConverter2 ).run();
		//new BrightnessAdjuster( bdvHandle, sourceAndConverter2, 10, 255.0 ).run();


		// Initializes static SourceService and Display Service
		BdvService.InitScijavaServices();

		// Gets active BdvHandle instance
		BdvHandle bdvHandle = BdvService.getSourceDisplayService().getActiveBdv();

		// Import SpimData object
		SpimDataImporterXML sdix = new SpimDataImporterXML("src/test/resources/mri-stack.xml");

		AbstractSpimData asd = sdix.get();

		// Register to the source service
		BdvService.getSourceService().register(asd);

		/*
		BdvService.getSourceService().getSourcesFromSpimdata(asd).forEach(source -> {
			BdvService.getSourceDisplayService().show(bdvHandle, source);

			new ViewerTransformAdjuster(bdvHandle, source).run();
			new BrightnessAutoAdjuster(source, 0).run();
		});

		// Import SpimData object
		sdix = new SpimDataImporterXML("src/test/resources/mri-stack-shiftedX.xml");

		asd = sdix.get();

		// Register to the source service
		BdvService.getSourceService().register(asd);

		BdvService.getSourceService().getSourcesFromSpimdata(asd).forEach(source -> {
			BdvService.getSourceDisplayService().show(bdvHandle, source);

			new ViewerTransformAdjuster(bdvHandle, source).run();
			new BrightnessAutoAdjuster(source, 0).run();
		});*/


	}

	/*public static BdvHandle createBdv()
	{
		// specify average projector factory
		final BdvOptions bdvOptions = new BdvOptions()
				.accumulateProjectorFactory( AccumulateAverageProjectorARGB.factory );

		final BdvCreator bdvCreator = new BdvCreator( bdvOptions );
		bdvCreator.run();
		return bdvCreator.get();
	}*/
}
