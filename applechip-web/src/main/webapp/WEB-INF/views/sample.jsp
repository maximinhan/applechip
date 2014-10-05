<form:form id="form" method="post" modelAttribute="formDTO">

    <form:input path="messageFromUser" />

    <form:errors path="messageFromUser" cssClass="errorMessage" element="div" />

    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <spring:message code="message.youWrote" arguments="${message}" htmlEscape="true" />
        </div>
    </c:if>

    <button type="submit" class="btn">Submit</button>

</form:form>
