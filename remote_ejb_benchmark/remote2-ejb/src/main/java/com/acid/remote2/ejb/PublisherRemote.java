package com.acid.remote2.ejb;

import javax.ejb.Remote;

/**
 * Remote interface for Publisher enterprise bean. Declares one
 * business method.
 */
@Remote
public interface PublisherRemote {
    public void publishNews();
}
