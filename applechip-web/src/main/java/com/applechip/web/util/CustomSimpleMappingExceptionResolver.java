package com.applechip.web.util;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Slf4j
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
//	private static final String REQUEST_HEADER_TYPE = "accept";
//
//	private static final String REQUEST_HEADER_TYPE_JSON = "application/json";
//
//	private static final String REQUEST_HEADER_TYPE_HTML = "text/html";
//
//	private static final String REQUEST_HEADER_TYPE_XML = "application/xml";
//
//	private static final String PARAMETER_NAME_IFRAME_AJAXPOST = "rettype";
//
//	private static final String PARAMETER_VALUE_JSON = "json";
//
//	@Override
//	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
//			Exception ex) {
//		log.debug(String.format("REQUEST_HEADER_TYPE:%s", request.getHeader(REQUEST_HEADER_TYPE)));
//		log.debug(String.format("PARAMETER_NAME_IFRAME_AJAXPOST:%s",
//				request.getParameter(PARAMETER_NAME_IFRAME_AJAXPOST)));
//		ResponseVO responseVO = getResponseVO(ex);
//		log.info(String.format("exceptionCode:%s, message:%s, debugMessage:%s", responseVO.getReturnCode(),
//				responseVO.getMessage(), responseVO.getDebugMessage()));
//
//		writeLogIfNeed(request, ex, responseVO);
//		ModelAndView modelAndView = getModelAndView(request, ex, responseVO);
//		return modelAndView;
//	}
//
//	private ResponseVO getResponseVO(Exception ex) {
//		ResponseVO responseVO = new ResponseVO();
//
//		if (ex instanceof BindingException) {
//			responseVO = ((BindingException) ex).getResponseVO();
//		}
//
//		String exceptionCode = getExceptionCode(ex);
//		String exceptionMessage = getExceptionMessageByExceptionCode(exceptionCode);
//		String exceptionDebugMessage = ex.getMessage();
//		if (ex instanceof BaseResponseException) {
//			exceptionMessage = ((BaseResponseException) ex).getExceptionMessage();
//			exceptionDebugMessage = ((BaseResponseException) ex).getExceptionDebugMessage();
//		}
//
//		responseVO.setReturnCode(exceptionCode);
//		responseVO.setMessage(exceptionMessage);
//		responseVO.setDebugMessage(exceptionDebugMessage);
//		return responseVO;
//	}

}
