package sc.fiji.bdvpg.converter;

import net.imglib2.converter.Converter;
import net.imglib2.display.ColorConverter;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.RealType;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO : documentation - why is this here ? Is it for the transparency ?
 * @param <R>
 */

public abstract class RealARGBColorConverter< R extends RealType< ? > > implements ColorConverter, Converter< R, ARGBType >
{
	protected double min = 0;

	protected double max = 1;

	protected final ARGBType color = new ARGBType( ARGBType.rgba( 255, 255, 255, 255 ) );

	protected int A;

	protected double scaleR;

	protected double scaleG;

	protected double scaleB;

	public RealARGBColorConverter( final double min, final double max )
	{
		this.min = min;
		this.max = max;
		update();
	}


	protected int black;

	// specify special colors for special values
	protected Map< Double, Integer > valueToColor = new HashMap<>(  );

	public Map< Double, Integer > getValueToColor()
	{
		return valueToColor;
	}

	@Override
	public ARGBType getColor()
	{
		return color.copy();
	}

	@Override
	public void setColor( final ARGBType c )
	{
		color.set( c );
		update();
	}

	@Override
	public boolean supportsColor()
	{
		return true;
	}

	@Override
	public double getMin()
	{
		return min;
	}

	@Override
	public double getMax()
	{
		return max;
	}

	@Override
	public void setMax( final double max )
	{
		this.max = max;
		update();
	}

	@Override
	public void setMin( final double min )
	{
		this.min = min;
		update();
	}

	private void update()
	{
		final double scale = 1.0 / ( max - min );
		final int value = color.get();
		A = ARGBType.alpha( value );
		scaleR = ARGBType.red( value ) * scale;
		scaleG = ARGBType.green( value ) * scale;
		scaleB = ARGBType.blue( value ) * scale;
		black = ARGBType.rgba( 0, 0, 0, A );
	}

	public static class Imp0< R extends RealType< ? > > extends RealARGBColorConverter< R >
	{
		public Imp0( final double min, final double max )
		{
			super( min, max );
		}

		@Override
		public void convert( final R input, final ARGBType output )
		{
			final double value = input.getRealDouble();

			if ( valueToColor.containsKey( value ) )
			{
				output.set( valueToColor.get( value ) );
				return;
			}

			final double valueMinusMin = value - min;
			if ( valueMinusMin < 0 )
			{
				output.set( black );
			}
			else
			{
				final int r0 = ( int ) ( scaleR * valueMinusMin + 0.5 );
				final int g0 = ( int ) ( scaleG * valueMinusMin + 0.5 );
				final int b0 = ( int ) ( scaleB * valueMinusMin + 0.5 );
				final int r = Math.min( 255, r0 );
				final int g = Math.min( 255, g0 );
				final int b = Math.min( 255, b0 );
				output.set( ARGBType.rgba( r, g, b, A) );
			}
		}
	}

	public static class Imp1< R extends RealType< ? > > extends RealARGBColorConverter< R >
	{
		public Imp1( final double min, final double max )
		{
			super( min, max );
		}

		@Override
		public void convert( final R input, final ARGBType output )
		{
			final double value = input.getRealDouble();

			if ( valueToColor.containsKey( value ) )
			{
				output.set( valueToColor.get( value ) );
				return;
			}

			final double valueMinusMin = value - min;
			if ( valueMinusMin < 0 )
			{
				output.set( black );
			}
			else
			{
				final int r0 = ( int ) ( scaleR * valueMinusMin + 0.5 );
				final int g0 = ( int ) ( scaleG * valueMinusMin + 0.5 );
				final int b0 = ( int ) ( scaleB * valueMinusMin + 0.5 );
				final int r = Math.min( 255, r0 );
				final int g = Math.min( 255, g0 );
				final int b = Math.min( 255, b0 );
				output.set( ARGBType.rgba( r, g, b, A) );
			}
		}
	}
}
