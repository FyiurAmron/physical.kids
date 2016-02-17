package vax.openglue.mesh;

import java.util.*;
import gnu.trove.map.hash.TObjectFloatHashMap;
import vax.math.Matrix4f;

/**

 @author toor
 */
public class CameraDistanceSorter implements MeshSorter {
    private final TObjectFloatHashMap<MeshInstance> distMap = new TObjectFloatHashMap<>();
    private Matrix4f cameraPosition;
    private ArrayList<MeshInstance> meshes;
    private final Comparator<MeshInstance> //
            ascendingDepthComparator = (MeshInstance o1, MeshInstance o2) -> Float.compare( distMap.get( o1 ), distMap.get( o2 ) ),
            descendingDepthComparator = (MeshInstance o1, MeshInstance o2) -> Float.compare( distMap.get( o2 ), distMap.get( o1 ) );

    public CameraDistanceSorter () {
    }

    public CameraDistanceSorter ( Matrix4f cameraPosition ) {
        this.cameraPosition = cameraPosition;
    }

    public Matrix4f getCameraPosition () {
        return cameraPosition;
    }

    public void setCameraPosition ( Matrix4f cameraPosition ) {
        this.cameraPosition = cameraPosition;
    }

    private MeshInstance prepareMeshInstanceInfo ( MeshInstance mesh ) {
        distMap.put( mesh, mesh.getTransform().calcDistance( cameraPosition ) );
        return mesh;
    }

    @Override
    public List<MeshInstance> sort ( Collection<MeshInstance> input, boolean ascending ) {
        if ( meshes == null ) {
            meshes = new ArrayList<>( input.size() );
        }
        int max = Math.min( meshes.size(), input.size() ); // yup
        Iterator<MeshInstance> it = input.iterator();

        for( int i = 0; i < max; i++ ) {
            meshes.set( i, prepareMeshInstanceInfo( it.next() ) );
        }
        while( it.hasNext() ) {
            meshes.add( prepareMeshInstanceInfo( it.next() ) );
        }

        meshes.sort( ascending ? ascendingDepthComparator : descendingDepthComparator );
        return meshes;
    }
}
