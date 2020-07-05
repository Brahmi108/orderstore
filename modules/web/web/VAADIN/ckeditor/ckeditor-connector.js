com_sweethill_orderstore_web_toolkit_ui_ckeditor_CKEditorServerComponent = function() {
    var connector = this;
    var element = connector.getElement();
    $(element).html("<textarea/>");
    CKEDITOR.replace(element);
}