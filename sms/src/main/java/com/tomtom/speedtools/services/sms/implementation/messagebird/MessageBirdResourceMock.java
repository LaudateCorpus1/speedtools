/*
 * Copyright (C) 2012-2019, TomTom NV (http://tomtom.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomtom.speedtools.services.sms.implementation.messagebird;

import com.tomtom.speedtools.services.sms.SMSDeliveryReportListener;
import com.tomtom.speedtools.services.sms.SMSDeliveryReportListener.DeliveryStatus;
import com.tomtom.speedtools.services.sms.SMSDeliveryReportListenerRegistry;
import com.tomtom.speedtools.services.sms.implementation.messagebird.dto.MessageBirdResponse;
import com.tomtom.speedtools.services.sms.implementation.messagebird.dto.MessageBirdResponse.Item;
import com.tomtom.speedtools.services.sms.implementation.messagebird.dto.MessageBirdResponse.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Link.Builder;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Mock that always returns SENT when used to send a message.
 */
public class MessageBirdResourceMock implements MessageBirdResource {
    private static final Logger LOG = LoggerFactory.getLogger(MessageBirdResourceMock.class);

    @Nonnull
    private final SMSDeliveryReportListenerRegistry smsDeliveryReportListenerRegistry;

    public MessageBirdResourceMock(@Nonnull final SMSDeliveryReportListenerRegistry smsDeliveryReportListenerRegistry) {
        assert smsDeliveryReportListenerRegistry != null;

        this.smsDeliveryReportListenerRegistry = smsDeliveryReportListenerRegistry;
    }


    @Override
    public Response sendMessage(
            @Nonnull final String userName,
            @Nonnull final String password,
            @Nullable final Long reference,
            @Nonnull final String sender,
            @Nonnull final String destination,
            @Nonnull final String body,
            @Nonnull final ResponseType responseType,
            @Nullable final String replaceChars) {

        assert userName != null;
        assert password != null;
        assert sender != null;
        assert destination != null;
        assert body != null;
        assert responseType != null;

        final ResponseCode status =
                ResponseCode.REQUEST_SUCCESSFUL;

        LOG.debug("sendMessage: Status={}, username={}, password={}, from={}, to={}, text={}, " +
                        "responseType={}, clientReference={}",
                status, userName, password, sender, destination, body, responseType, reference);

        final MessageBirdResponse response = new MessageBirdResponse();
        final Item item = new Item();
        response.setItem(item);

        item.setResponseCode(status);
        item.setResponseMessage("Message");

        // Return a delivery notification first (This only works because the listener is the SMSActor which will
        // process the delivery report asynchronously).
        final SMSDeliveryReportListener listener = smsDeliveryReportListenerRegistry.getListener();
        if ((listener != null) && (reference != null)) {
            listener.messageDeliveryReport(reference, DeliveryStatus.DELIVERED);
        }
        return new Response() {

            @Override
            @Nullable
            public MessageBirdResponse getEntity() {
                return response;
            }

            @Override
            public <T> T readEntity(final Class<T> aClass) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> T readEntity(final javax.ws.rs.core.GenericType<T> genericType) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> T readEntity(final Class<T> aClass, final Annotation[] annotations) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> T readEntity(final javax.ws.rs.core.GenericType<T> genericType, final Annotation[] annotations) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasEntity() {
                return false;
            }

            @Override
            public boolean bufferEntity() {
                return false;
            }

            @Override
            public void close() {
                // Left empty.
            }

            @Override
            public MediaType getMediaType() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Locale getLanguage() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Set<String> getAllowedMethods() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Map<String, NewCookie> getCookies() {
                throw new UnsupportedOperationException();
            }

            @Override
            public EntityTag getEntityTag() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Date getDate() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Date getLastModified() {
                throw new UnsupportedOperationException();
            }

            @Override
            public URI getLocation() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Set<javax.ws.rs.core.Link> getLinks() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasLink(final String s) {
                return false;
            }

            @Override
            public javax.ws.rs.core.Link getLink(final String s) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Builder getLinkBuilder(final String s) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getStatus() {
                return Response.Status.OK.getStatusCode();
            }

            @Override
            public StatusType getStatusInfo() {
                throw new UnsupportedOperationException();
            }

            @Override
            @Nonnull
            public MultivaluedMap<String, Object> getMetadata() {
                throw new UnsupportedOperationException();
            }

            @Override
            public MultivaluedMap<String, String> getStringHeaders() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getHeaderString(final String s) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
