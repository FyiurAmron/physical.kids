package vax.openglue.mesh;

import java.util.*;
import gnu.trove.map.hash.TObjectFloatHashMap;
import vax.math.Matrix4f;

/**

 @author toor
 */
public class CameraDistanceSorter implements MeshSorter {
    private final TObjectFloatHashMap<MeshInstance> distMap = new TObjectFloatHashMap<>();
    private Matrix4f viewMatrix;
    private final Matrix4f cameraMatrix = new Matrix4f();
    private ArrayList<MeshInstance> meshes;
    private final Comparator<MeshInstance> //
            ascendingDepthComparator = (MeshInstance o1, MeshInstance o2) -> Float.compare( distMap.get( o1 ), distMap.get( o2 ) ),
            descendingDepthComparator = (MeshInstance o1, MeshInstance o2) -> Float.compare( distMap.get( o2 ), distMap.get( o1 ) );

    public CameraDistanceSorter () {
    }

    public CameraDistanceSorter ( Matrix4f viewMatrix ) {
        this.viewMatrix = viewMatrix;
    }

    public Matrix4f getViewMatrix () {
        return viewMatrix;
    }

    public void setViewMatrix ( Matrix4f viewMatrix ) {
        this.viewMatrix = viewMatrix;
    }

    private MeshInstance prepareMeshInstanceInfo ( MeshInstance mesh ) {
        distMap.put( mesh, mesh.getTransform().calcTranslationDistanceSq( cameraMatrix ) );
        return mesh;
    }

    @Override
    public List<MeshInstance> sort ( Collection<MeshInstance> input, boolean ascending ) {
        if ( meshes == null ) {
            meshes = new ArrayList<>( input.size() );
        }
        int inputSize = input.size();
        if ( inputSize == 0 ) {
            meshes.clear();
            return meshes;
        }
        viewMatrix.invert( cameraMatrix );
        Iterator<MeshInstance> it = input.iterator();
        int meshesSize = meshes.size();
        if ( meshesSize == inputSize ) {
            for( int i = 0; i < inputSize; i++ ) {
                meshes.set( i, prepareMeshInstanceInfo( it.next() ) );
            }
        } else if ( meshesSize < inputSize ) {
            for( int i = 0; i < meshesSize; i++ ) {
                meshes.set( i, prepareMeshInstanceInfo( it.next() ) );
            }
            while( it.hasNext() ) {
                meshes.add( prepareMeshInstanceInfo( it.next() ) );
            }
        } else { // meshesSize > inputSize
            for( int i = 0; i < inputSize; i++ ) {
                meshes.set( i, prepareMeshInstanceInfo( it.next() ) );
            }
            meshes.subList( inputSize, meshesSize ).clear();
        }

        meshes.sort( ascending ? ascendingDepthComparator : descendingDepthComparator );
        return meshes;
    }
}
