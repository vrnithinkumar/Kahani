package com.example.LIJIN.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 * TODO null check etc
 */
@Api(
        name = "storyApi",
        version = "v1",
        resource = "story",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.LIJIN.example.com",
                ownerName = "backend.myapplication.LIJIN.example.com",
                packagePath = ""
        )
)
public class StoryEndpoint {

    private static final Logger logger = Logger.getLogger(StoryEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Story.class);
    }

    /**
     * Returns the {@link Story} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Story} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "story/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Story get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Story with ID: " + id);
        Story story = ofy().load().type(Story.class).id(id).now();
        if (story == null) {
            throw new NotFoundException("Could not find Story with ID: " + id);
        }
        return story;
    }

    /**
     * Inserts a new {@code Story}.
     */
    @ApiMethod(
            name = "insert",
            path = "story",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Story insert(Story story) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that story.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(story).now();
        logger.info("Created Story with ID: " + story.getId());

        return ofy().load().entity(story).now();
    }

    /**
     * Updates an existing {@code Story}.
     *
     * @param id    the ID of the entity to be updated
     * @param story the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Story}
     */
    @ApiMethod(
            name = "update",
            path = "story/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Story update(@Named("id") Long id, Story story) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(story).now();
        logger.info("Updated Story: " + story);
        return ofy().load().entity(story).now();
    }

    /**
     * Deletes the specified {@code Story}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Story}
     */
    @ApiMethod(
            name = "remove",
            path = "story/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Story.class).id(id).now();
        logger.info("Deleted Story with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "story",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Story> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Story> query = ofy().load().type(Story.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Story> queryIterator = query.iterator();
        List<Story> storyList = new ArrayList<Story>(limit);
        while (queryIterator.hasNext()) {
            storyList.add(queryIterator.next());
        }
        return CollectionResponse.<Story>builder().setItems(storyList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Story.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Story with ID: " + id);
        }
    }
}