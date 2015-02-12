Ext.define('Supplier.store.OrderManage', {
    //不要忘了继承
    extend: 'Ext.data.Store',
    //记得设置model
    model: 'Supplier.model.OrderManage',
    proxy: {
        type: 'ajax',
        url: '/orders',
        reader: {
            type: 'json',
            successProperty: 'success',
            root: 'data.list',
            messageProperty: 'message'
        }
    },
    autoLoad: true
});