package com.onepiece.redwood.customer.contracts;

import android.content.Context;

/**
 * Created by Administrator on 2015/8/25.
 */
public interface IContractBiz {
    void getPhoneContacts(Context context,OnPhoneContactsListener onPhoneContactsListener);
}
