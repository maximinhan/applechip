package com.applechip.core.abstact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.entity.User;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class GenericByCreated<PK extends Serializable> extends GenericDtUpdated<PK> {

  @Column(name = "created_by", insertable = true, updatable = false, length = ColumnSizeConstant.UUID)
  protected String createdBy;

  @PrePersist
  public void setCreatedBy() {
    String currentUserId = getCurrentUserId();
    this.createdBy = currentUserId;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  protected String getCurrentUserId() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    User currentUser = null;
    if (ctx.getAuthentication() != null) {
      Authentication auth = ctx.getAuthentication();
      AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
      boolean signupUser = resolver.isAnonymous(auth);
      if (!signupUser) {
        if (auth.getPrincipal() instanceof UserDetails) {
          currentUser = (User) auth.getPrincipal();
        } else if (auth.getDetails() instanceof UserDetails) {
          currentUser = (User) auth.getDetails();
        }
      }
    }
    return currentUser == null ? null : currentUser.getId();
  }
}
