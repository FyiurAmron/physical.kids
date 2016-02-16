package vax.openglue;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**

 @author toor
 */
public interface UniformDescriptor {
    @Retention( RetentionPolicy.RUNTIME )
    @Target( ElementType.FIELD )
    @interface PerMeshUniform {
    }

    @Retention( RetentionPolicy.RUNTIME )
    @Target( ElementType.FIELD )
    @interface PerRenderUniform {
    }

    class Reflective {
        public ArrayList<Uniform> perRenderUniforms = new ArrayList<>(), perMeshUniforms = new ArrayList<>();

        public void init () {
            for( Field f : getClass().getFields() ) {
                if ( f.isAnnotationPresent( PerRenderUniform.class ) ) {
                    if ( f.isAnnotationPresent( PerMeshUniform.class ) ) {
                        throw new UnsupportedOperationException(
                                "field has to be either a PerRenderUniform or PerMeshUniform (or none), not both" );
                    }
                    perRenderUniforms.add( Uniform.from( f, this ) );
                } else if ( f.isAnnotationPresent( PerMeshUniform.class ) ) {
                    perMeshUniforms.add( Uniform.from( f, this ) );
                }
            }
        }

    }
}
