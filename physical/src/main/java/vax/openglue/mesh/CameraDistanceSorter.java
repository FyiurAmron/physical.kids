package vax.openglue.mesh;

import java.util.*;
import gnu.trove.map.hash.TObjectFloatHashMap;
import vax.math.Matrix4f;

/**

 @author toor
 */
public class CameraDistanceSorter implements MeshSorter {
    private final TObjectFloatHashMap<Mesh> distMap = new TObjectFloatHashMap<>();
    private Matrix4f cameraPosition;
    private ArrayList<Mesh> meshes;
    private final Comparator<Mesh> //
            ascendingDepthComparator = (Mesh o1, Mesh o2) -> Float.compare( distMap.get( o1 ), distMap.get( o2 ) ),
            descendingDepthComparator = (Mesh o1, Mesh o2) -> Float.compare( distMap.get( o2 ), distMap.get( o1 ) );

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

    private Mesh prepareMeshInfo ( Mesh mesh ) {
        distMap.put( mesh, mesh.getTransform().calcDistance( cameraPosition ) );
        return mesh;
    }

    @Override
    public List<Mesh> sort ( Collection<Mesh> input, boolean ascending ) {
        if ( meshes == null ) {
            meshes = new ArrayList<>( input.size() );
        }
        int max = Math.min( meshes.size(), input.size() ); // yup
        Iterator<Mesh> it = input.iterator();

        for( int i = 0; i < max; i++ ) {
            meshes.set( i, prepareMeshInfo( it.next() ) );
        }
        while( it.hasNext() ) {
            meshes.add( prepareMeshInfo( it.next() ) );
        }

        meshes.sort( ascending ? ascendingDepthComparator : descendingDepthComparator );
        return meshes;
    }
}
