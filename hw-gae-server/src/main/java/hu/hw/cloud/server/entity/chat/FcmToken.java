/**
 * 
 */
package hu.hw.cloud.server.entity.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import hu.hw.cloud.shared.dto.common.FcmTokenDto;

/**
 * @author robi
 *
 */
public class FcmToken {

	private String token;

	private String userAgent;

	private Date created;

	public FcmToken() {
	}

	public FcmToken(String token, String userAgent) {
		this();
		this.token = token;
		this.userAgent = userAgent;
		this.created = new Date();
	}

	public FcmToken(FcmTokenDto dto) {
		this();
		this.token = dto.getToken();
		this.userAgent = dto.getUserAgent();
		this.created = dto.getCreated();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public static Comparator<FcmToken> ORDER_BY_TOKEN = new Comparator<FcmToken>() {
		public int compare(FcmToken one, FcmToken other) {
			return one.getToken().compareTo(other.getToken());
		}
	};

	public static FcmToken getToken(List<FcmToken> tokens, final String token) {
		Predicate<FcmToken> condition = new Predicate<FcmToken>() {
			public boolean evaluate(FcmToken object) {
				return (object.getToken().equals(token));
			}
		};
		List<FcmToken> result = (List<FcmToken>) CollectionUtils.select(tokens, condition);

		if (result.isEmpty())
			return null;

		return Collections.max(result, FcmToken.ORDER_BY_TOKEN);
	}

	public static FcmTokenDto createDto(FcmToken entity) {
		FcmTokenDto dto = new FcmTokenDto();
		dto = entity.updateDto(dto);
		return dto;
	}

	public FcmTokenDto updateDto(FcmTokenDto dto) {

		if (this.getToken() != null)
			dto.setToken(this.getToken());

		if (this.getUserAgent() != null)
			dto.setUserAgent(this.getUserAgent());

		if (this.getCreated() != null)
			dto.setCreated(this.getCreated());

		return dto;
	}

	public static List<FcmTokenDto> createDtos(List<FcmToken> entities) {
		List<FcmTokenDto> dtos = new ArrayList<FcmTokenDto>();
		for (FcmToken entity : entities)
			dtos.add(createDto(entity));
		return dtos;
	}

	public static List<FcmToken> createList(List<FcmTokenDto> dtos) {
		List<FcmToken> result = new ArrayList<FcmToken>();
		for (FcmTokenDto dto : dtos) {
			result.add(new FcmToken(dto));
		}
		return result;
	}

}
