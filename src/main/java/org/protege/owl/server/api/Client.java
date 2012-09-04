package org.protege.owl.server.api;

import java.util.Collection;
import java.util.SortedSet;

import org.protege.owl.server.api.exception.OWLServerException;
import org.semanticweb.owlapi.model.IRI;

public interface Client {
	
	String getScheme();
	
	String getAuthority();
	
	IRI getServerIRI();
	
	UserId getUserId();
	
	boolean isCompatible(VersionedOntologyDocument versionedOntology);
	
	/**
	 * This call gets the document factory.  This factory defines how the Client implements ChangeDocuments and
	 * how the client retrieves information about a remote server associated with an ontology stored on a local disk.
	 * 
	 * @return
	 */
	DocumentFactory getDocumentFactory();
	
    OntologyDocumentRevision evaluateRevisionPointer(RemoteOntologyDocument doc, RevisionPointer pointer) throws OWLServerException;

	
	/**
	 * If you pass an address for an object on the server, the server will try to return the
	 * ServerDocument associated with that address.
	 * <p/>
	 * The ServerDocument might be either a RemoteOntologyDocument or a ServerDirectory.
	 * 
	 * @param serverIRI
	 * @return
	 */
	RemoteServerDocument getServerDocument(IRI serverIRI) throws OWLServerException;
	
	
	/**
	 * Returns a list of the Server documents in a server directory
	 * 
	 * @param path
	 * @return
	 */
	Collection<RemoteServerDocument> list(RemoteServerDirectory path) throws OWLServerException;
	
	/**
	 * Allows the user to create a directory on the server.
	 * 
	 * @param serverIRI
	 */
	RemoteServerDirectory createRemoteDirectory(IRI serverIRI) throws OWLServerException;
	
	/**
	 * Allows the user to create an empty ontology document on the server.
	 * <p/>
	 * A typical pattern will be for the user to create a remote ontology document with this call and 
	 * then commit a collection of changes filling in the remote content with the commit call.
	 *
	 * @param serverIRI
	 * @return
	 */
	RemoteOntologyDocument createRemoteOntology(IRI serverIRI) throws OWLServerException;
		
	
	/**
	 * Retrieves the list of changes for the RemoteOntology Document from a given start revision
	 * to a given end revision.  
	 * 
	 * @param document
	 * @param start
	 * @param end
	 * @return
	 */
	ChangeHistory getChanges(RemoteOntologyDocument document, RevisionPointer start, RevisionPointer end) throws OWLServerException;
	
	/**
	 * Commits a collection of changes to the remote ontology document.
	 * 
	 * @param document
	 * @param revision
	 * @param changes
	 */
	void commit(RemoteOntologyDocument document, 
	             SingletonChangeHistory changes) throws OWLServerException;
	
	void shutdown() throws OWLServerException;
}
