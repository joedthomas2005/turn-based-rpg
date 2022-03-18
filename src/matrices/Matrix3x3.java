package matrices;

public class Matrix3x3 extends Matrix{
	public Matrix3x3(float tl, float tc, float tr,
					float cl, float cc, float cr,
					float bl, float bc, float br) {
		
		super(new float[][] {{tl,tc,tr},{cl,cc,cr},{bl,bc,br}});
	}
	
	public Matrix add(Matrix other) {
		return new Matrix3x3(0,0,0,0,0,0,0,0,0);
	}
	
	public Matrix multiply(Matrix other) {
		return new Matrix3x3(0,0,0,0,0,0,0,0,0);
	}
}
