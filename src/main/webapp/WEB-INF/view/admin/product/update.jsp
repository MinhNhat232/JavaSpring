<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Dashboard - Hỏi Dân IT</title>
                <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const imageFile = $("#imageFile");
                        const orgImage = "${currentProduct.image}";
                        if (orgImage) {
                            const urlImage = "/images/product/" + orgImage;
                            $("#avatarPreview").attr("src", urlImage);
                            $("#avatarPreview").css({ "display": "block" });
                        }
                        imageFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-7">
                                <h1 class="mt-0">Manage Products</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Products</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create a product</h3>
                                            <hr />
                                            <form:form method="post" enctype="multipart/form-data"
                                                action="/admin/product/update" modelAttribute="currentProduct">

                                                <c:set var="nameHasBindError">
                                                    <form:errors path="name" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="priceHasBindError">
                                                    <form:errors path="price" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="detailDesHasBindError">
                                                    <form:errors path="detailDes" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="shortDesHasBindError">
                                                    <form:errors path="shortDes" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="quantityHasBindError">
                                                    <form:errors path="quantity" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="factoryHasBindError">
                                                    <form:errors path="factory" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="targetHasBindError">
                                                    <form:errors path="target" cssClass="invalid-feedback" />
                                                </c:set>
                                                <div class="mb-3">
                                                    <label for="id" class="form-label">ID:</label>
                                                    <form:input type="text" class="form-control" path="id" name="id"
                                                        readonly="true" />
                                                </div>
                                                <div class="row g-4 mb-3">
                                                    <div class="col-md-6">
                                                        <label for="name" class="form-label">Product Name:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty nameHasBindError ? 'is-invalid' : ''}"
                                                            path="name" name="name" />
                                                        ${nameHasBindError}
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label for="price" class="form-label">Price:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty priceHasBindError ? 'is-invalid' : ''}"
                                                            path="price" name="price" />
                                                        ${priceHasBindError}
                                                    </div>
                                                </div>

                                                <div class="mb-3">
                                                    <label for="detail" class="form-label">Detail Description:</label>
                                                    <form:textarea
                                                        class="form-control ${not empty detailDesHasBindError ? 'is-invalid' : ''}"
                                                        path="detailDes" name="detail" rows="4"></form:textarea>
                                                    ${detailDesHasBindError}
                                                </div>

                                                <div class="row g-4 mb-3">
                                                    <div class="col-md-6">
                                                        <label for="password" class="form-label">Short
                                                            Description:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty shortDesHasBindError ? 'is-invalid' : ''}"
                                                            path="shortDes" name="shortDescription" />
                                                        ${shortDesHasBindError}
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label for="phone" class="form-label">Quantity:</label>
                                                        <form:input type="tel"
                                                            class="form-control ${not empty quantityHasBindError ? 'is-invalid' : ''}"
                                                            path="quantity" name="quantity" />
                                                        ${quantityHasBindError}

                                                    </div>
                                                </div>

                                                <div class="row g-4 mb-3">
                                                    <div class="col-md-6">
                                                        <label for="select" class="form-label">Factory:</label>
                                                        <form:select class="form-select" path="factory">
                                                            <form:option value="APPLE">Apple</form:option>
                                                            <form:option value="ASUS">Asus</form:option>
                                                            <form:option value="DELL">Dell</form:option>
                                                            <form:option value="HP">HP</form:option>
                                                            <form:option value="LENOVO">Lenovo</form:option>
                                                            <form:option value="MSI">MSI</form:option>
                                                        </form:select>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label for="select" class="form-label">Target:</label>
                                                        <form:select class="form-select" path="target">
                                                            <form:option value="GAMING">Gaming</form:option>
                                                            <form:option value="WORK">Work</form:option>
                                                            <form:option value="STUDENT">Student</form:option>
                                                            <form:option value="BUSINESS">Business</form:option>
                                                            <form:option value="PERSONAL">Personal</form:option>
                                                            <form:option value="OTHER">Other</form:option>
                                                        </form:select>
                                                    </div>
                                                </div>

                                                <div class="mb-3">
                                                    <label for="imageFile" class="form-label">Image:</label>
                                                    <input class="form-control" type="file" id="imageFile"
                                                        name="productFile" accept=".png, .jpg, .jpeg" />
                                                </div>

                                                <div class="col-12 mb-4">
                                                    <img id="avatarPreview" src="#" alt="Avatar Preview"
                                                        style="display: none; max-height: 250px; margin-top: 10px;" />
                                                </div>

                                                <button class="btn btn-primary mt-5">Submit</button>
                                            </form:form>
                                        </div>
                                    </div>

                                </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
                    crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
                    crossorigin="anonymous"></script>
            </body>

            </html>