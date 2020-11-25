package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.*;
import java.util.*;

public abstract class RoleAuthorityValue {

    public static class Simple extends AbstractDomainValue<RoleAuthority> {

        public Simple(RoleAuthority domain) {
            super(domain);
        }

        public Date getCreateTime() {
            return super.domain.getCreateTime();
        }

        public int getId() {
            return super.domain.getId();
        }
    }

    public static class Info extends Simple {

        public Info(RoleAuthority domain) {
            super(domain);
        }

        public AuthorityValue.Simple getAuthority() {
            return new AuthorityValue.Simple(super.domain.getAuthority());
        }

        public RoleValue.Simple getRole() {
            return new RoleValue.Simple(super.domain.getRole());
        }
    }

    public static class More extends Info {

        public More(RoleAuthority domain) {
            super(domain);
        }
    }
}
