$(document).ready(
		function() {

			var url = "http://143.89.144.156:5050/VR_CMS";

			$.ajax({
				type : "GET",
				url : url + "/getAllSchools",
				dataType : "json",
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						var div_data = "<option value=" + data[i].serverId
								+ ">" + data[i].schoolName + "</option>";
						$(div_data).appendTo('#serverList');

					}
				}
			});

			$('#contact_form').bootstrapValidator({
				// To use feedback icons, ensure that
				// you use Bootstrap v3.1.0 or later
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					name : {
						validators : {
							stringLength : {
								min : 2,
							},
							notEmpty : {
								message : 'Please enter name'
							}
						}
					},
					type : {
						validators : {
							notEmpty : {
								message : 'Please the type'
							}
						}
					},
					category : {
						validators : {
							notEmpty : {
								message : 'Please enter the category'
							},
						}
					},
					imageName : {
						validators : {
							stringLength : {
								min : 2,
							},
							notEmpty : {
								message : 'Please enter image name'
							}
						}
					},
					imageUpload : {
						validators : {
							notEmpty : {
								message : 'Please upload image'
							}
						}
					},
					videoName : {
						validators : {
							stringLength : {
								min : 2,
							},
							notEmpty : {
								message : 'Please enter video name'
							}
						}
					},
					videoUpload : {
						validators : {
							notEmpty : {
								message : 'Please upload video'
							}
						}
					},
					serverList : {
						validators : {
							notEmpty : {
								message : 'Please enter school servers'
							}
						}
					},
				}
			});

			$('#video_upload_btn').click(
					function() {
						var fileImage = document.getElementById('file-image');
						var imagefile = fileImage.files;

						var fileVideo = document.getElementById('file-video');
						var videofile = fileVideo.files;
						var formData = new FormData();
						formData.append('name',
								document.getElementById('name').value);
						formData.append('type',
								document.getElementById('type').value);
						formData.append('category', document
								.getElementById('category').value);
						formData.append('imageName', document
								.getElementById('imageName').value);
						formData.append('videoName', document
								.getElementById('videoName').value);
						formData.append('description', document
								.getElementById('description').value);
						formData.append('image', imagefile[0],
								imagefile[0].name);
						formData.append('video', videofile[0],
								videofile[0].name);
						formData.append('serverList', $('#serverList').val());

						var xhr = new XMLHttpRequest();

						(xhr.upload || xhr).addEventListener('progress',
								function(e) {
									var done = e.position || e.loaded
									var total = e.totalSize || e.total;
									var percentage = Math.round(done / total
											* 100);
									console.log('xhr progress: ' + percentage
											+ '%');
									$('.progress-bar').css('width',
											percentage + '%').attr(
											'aria-valuenow', percentage);

								});

						xhr.open('POST', url + '/addOrUpdateVideo', true);
						xhr.timeout = 4000000;
						xhr.onload = function(res) {
							if (xhr.status === 200) {
								console.log('connection made' + res);
								hidePleaseWait();
							} else {
								alert('An error occurred!');
							}
						};
						xhr.error = function(error) {
							console.log(error);
						}
						xhr.send(formData);
						var pleaseWait = $('#pleaseWaitDialog');

						showPleaseWait = function() {
							pleaseWait.modal('show');
						};

						hidePleaseWait = function() {
							pleaseWait.modal('hide');
						};

						showPleaseWait();
						// $.post($('#contact_form').attr('action',
						// url + "/addOrUpdateVideo"), $(
						// '#contact_form').serialize(),
						// function(
						// result) {
						// console.log(result);
						// }, 'json');
						console.log($('#contact_form').serialize());
					});

			$('#register_form').bootstrapValidator({
				// To use feedback icons, ensure that
				// you use Bootstrap v3.1.0 or later
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					name : {
						validators : {
							stringLength : {
								min : 2,
							},
							notEmpty : {
								message : 'Please enter name'
							}
						}
					},
					phone : {
						validators : {
							notEmpty : {
								message : 'Please supply your phone number'
							}
						}
					},
					address : {
						validators : {
							stringLength : {
								min : 8,
							},
							notEmpty : {
								message : 'Please supply your street address'
							}
						}
					},
					city : {
						validators : {
							stringLength : {
								min : 4,
							},
							notEmpty : {
								message : 'Please supply your city'
							}
						}
					},
				}
			});

			$('#register_school_btn').click(
					function() {
						$.post($('#register_form').attr('action',
								url + "/addOrUpdateSchool"),
								$('#register_form').serialize(), function(
										result) {
									console.log(result);
								}, 'json');
						console.log($('#register_form').serialize());
					});

		});