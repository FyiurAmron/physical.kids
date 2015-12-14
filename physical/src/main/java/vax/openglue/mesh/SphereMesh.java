package vax.openglue.mesh;

import vax.math.FloatUtils;
import vax.util.ArrayCompiler;

public class SphereMesh extends Mesh {
    /**
     Note: to create a dome (half-sphere), use mirror = false, mirrorCap = false.

     @param radius
     @param slices
     @param stacks
     @param outside
     @param mirror
     @param mirrorCap
     */
    public SphereMesh ( float radius, int slices, int stacks, boolean outside, boolean mirror, boolean mirrorCap ) {
        super( Builder.build( radius, slices, stacks, outside, mirror, mirrorCap ) );
    }

    public SphereMesh ( float radius, int slices, int stacks, boolean outside ) {
        this( radius, slices, stacks, outside, true, true );
    }

    public static class Builder {
        /** &lt; 0.5 creates distortion on last valid stack, &gt; 0.5 generates unnecessary 'sewing' on it */
        protected static final float CAP_RATIO = 0.5f;

        private float ds;
        private int nsign;
        private float[] msTheta, cTheta;
        private float rho, t;
        private float[][][][] vCache;

        private float radius;
        private int slices;
        //int stacks;
        //bool outside;
        private ArrayCompiler.Floats[] fac;

        protected static Builder instance = new Builder();

        protected Builder () {
        }

        protected void swapCache () {
            float[][][] tmp = vCache[0];
            vCache[0] = vCache[1];
            vCache[1] = tmp;
        }

        protected void putFAC ( float[][]... vs ) {
            if ( vs != null ) {
                for( float[][] fv : vs ) {
                    for( int i = 0; i < Mesh.VS_COUNT; i++ ) {
                        fac[i].put( fv[i] );
                    }
                }
            }
        }

        protected void setCache () {
            float s_rho = (float) Math.sin( rho ), c_rho = (float) Math.cos( rho ),
                    s = 0,
                    x, y, z;
            for( int j = 0; j <= slices; j++, s += ds ) {
                z = msTheta[j] * s_rho;
                y = c_rho * nsign;
                x = cTheta[j] * s_rho;
                float[][] fArr = new float[][]{
                    new float[]{ x * radius, y * radius, z * radius },
                    new float[]{ x * nsign, y * nsign, z * nsign },
                    new float[]{ s, t }
                };
                vCache[1][j] = fArr;
            }
        }

        protected MeshData buildInternal ( float radius, int slices, int stacks, boolean outside, boolean mirror, boolean mirrorCap ) {
            this.radius = radius;
            this.slices = slices;
            if ( stacks < 2 || slices < 2 ) {
                throw new IllegalArgumentException( "stacks < 2 || slices < 2" );
            }

            nsign = outside ? 1 : -1;
            // note: the caps are constructed differently vs GLU (due to CAP_RATIO)

            msTheta = new float[slices + 1];
            cTheta = new float[slices + 1];
            float theta = 0, dtheta = FloatUtils.TWO_PI / slices;
            for( int j = 0; j <= slices; j++, theta += dtheta ) {
                msTheta[j] = -(float) Math.sin( theta );
                cTheta[j] = (float) Math.cos( theta );
            }
            vCache = new float[2][][][];
            for( int i = 0; i < 2; i++ ) {
                vCache[i] = new float[slices + 1][][];
            }
            ds = 1.0f / slices;

            int ops = ( stacks + 1 ) * slices * 6; // stacks +1 for 2 'caps' with half tris at each sphere's 'end'
            fac = new ArrayCompiler.Floats[]{
                new ArrayCompiler.Floats( ops * Mesh.V_DIMS ),
                new ArrayCompiler.Floats( ops * Mesh.VN_DIMS ),
                new ArrayCompiler.Floats( ops * Mesh.VT_DIMS )
            };

            float drho = FloatUtils.PI / stacks, dt = 1.0f / stacks, s, half_ds = ds / 2;
            rho = drho * CAP_RATIO;
            t = dt * CAP_RATIO; // this is reversed vs GLU, since we draw top-to-bottom (correct alignment)

            setCache(); // cap 1
            s = half_ds; // pushed to the middle to make the trifan smoother; use s = 0 for legacy (GLU) rendering
            for( int j = 0; j < slices; j++, s += ds ) {
                putFAC(
                        vCache[1][j + 1],
                        new float[][]{
                            new float[]{ 0, radius * nsign, 0 },
                            new float[]{ 0, nsign, 0 },
                            new float[]{ s, 0 }
                        },
                        vCache[1][j] );
            }
            swapCache();
            rho = drho;
            t = dt;

            if ( !mirror ) {
                stacks /= 2;
                stacks++;
            }
            for( int i = 1; i < stacks; i++, rho += drho, t += dt ) {
                setCache();
                for( int j = 0; j < slices; j++ ) {
                    putFAC(
                            vCache[0][j], vCache[1][j], vCache[0][j + 1],
                            vCache[1][j + 1], vCache[0][j + 1], vCache[1][j] );
                }
                swapCache();
            }

            if ( mirrorCap ) {
                rho = FloatUtils.PI - drho * CAP_RATIO;
                t = 1 - dt * CAP_RATIO;
                setCache();
                for( int j = 0; j < slices; j++ ) {
                    putFAC(
                            vCache[0][j], vCache[1][j], vCache[0][j + 1],
                            vCache[1][j + 1], vCache[0][j + 1], vCache[1][j] );
                }
                swapCache();

                s = half_ds; // cap 2
                for( int j = 0; j < slices; j++, s += ds ) // last slice with tip
                {
                    putFAC(
                            vCache[0][j],
                            new float[][]{
                                new float[]{ 0, -radius * nsign, 0 },
                                new float[]{ 0, -nsign, 0 },
                                new float[]{ s, 1 }
                            },
                            vCache[0][j + 1] );
                }
            }
            //return new ModelData( fac[0].compile(), fac[1].compile(), fac[2].compile() );
            return new MeshData( fac[0].compileTruncate(), fac[1].compileTruncate(), fac[2].compileTruncate() );
            // TODO optimize indices
        }

        public static MeshData build ( float radius, int slices, int stacks, boolean outside, boolean mirror, boolean mirrorCap ) {
            return instance.buildInternal( radius, slices, stacks, outside, mirror, mirrorCap );
        }
    }
}
